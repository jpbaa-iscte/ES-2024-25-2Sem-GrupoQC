package es.qc;

import java.util.*;

/**
 * Classe responsável por sugerir trocas de propriedades entre diferentes donos,
 * com base em critérios como ganho de área média agrupada e penalizações por diferença
 * de área e comprimento.
 */
public class SugestorTrocas {

    /**
     * Gera uma lista de trocas sugeridas entre propriedades vizinhas de diferentes donos.
     * Apenas trocas com score positivo são consideradas.
     *
     * @param propriedades Um mapa com todas as propriedades indexadas pelo seu identificador.
     * @return Lista de trocas sugeridas, ordenadas por score decrescente.
     */
    public static List<TrocaSugerida> sugerirTrocas(Map<String, Propriedade> propriedades) {
        List<TrocaSugerida> trocas = new ArrayList<>();
        Set<String> paresAvaliados = new HashSet<>();

        for (Propriedade p1 : propriedades.values()) {
            for (String vizinhoId : p1.getVizinhos()) {
                Propriedade p2 = propriedades.get(vizinhoId);
                if (p2 == null || p1.getOwner().equals(p2.getOwner())) continue;

                String chavePar = p1.getParId() + ":" + p2.getParId();
                String chaveInversa = p2.getParId() + ":" + p1.getParId();
                if (paresAvaliados.contains(chavePar) || paresAvaliados.contains(chaveInversa)) continue;
                paresAvaliados.add(chavePar);

                double score = avaliarTroca(p1, p2, propriedades);
                if (score > 0) {
                    trocas.add(new TrocaSugerida(p1, p2, score));
                }
            }
        }

        trocas.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        return trocas;
    }

    /**
     * Avalia uma troca hipotética entre duas propriedades, com base no ganho de área média agrupada
     * e penalizações por diferença de área e comprimento.
     *
     * @param p1 Primeira propriedade.
     * @param p2 Segunda propriedade.
     * @param todas Mapa com todas as propriedades.
     * @return O score da troca; valores positivos indicam trocas vantajosas.
     */
    private static double avaliarTroca(Propriedade p1, Propriedade p2, Map<String, Propriedade> todas) {
        String owner1 = p1.getOwner();
        String owner2 = p2.getOwner();

        // Cálculo da área média agrupada antes da troca
        double mediaAntes = CalcularMedia.calcularAreaMediaAgrupada(todas, "freguesia", p1.getFreguesia())
                + CalcularMedia.calcularAreaMediaAgrupada(todas, "freguesia", p2.getFreguesia());

        // Trocar donos temporariamente
        p1.setOwner(owner2);
        p2.setOwner(owner1);

        // Cálculo da área média agrupada depois da troca
        double mediaDepois = CalcularMedia.calcularAreaMediaAgrupada(todas, "freguesia", p1.getFreguesia())
                + CalcularMedia.calcularAreaMediaAgrupada(todas, "freguesia", p2.getFreguesia());

        // Reverter troca
        p1.setOwner(owner1);
        p2.setOwner(owner2);

        double ganhoAreaMedia = mediaDepois - mediaAntes;

        // Penalizações
        double diffArea = Math.abs(p1.getArea() - p2.getArea());
        double diffComprimento = Math.abs(p1.getComprimento() - p2.getComprimento());

        // Score final
        return ganhoAreaMedia - 0.1 * diffArea - 0.05 * diffComprimento;
    }
}

/**
 * Representa uma sugestão de troca entre duas propriedades, com um score associado.
 */
class TrocaSugerida {
    private final Propriedade p1, p2;
    private final double score;

    /**
     * Cria uma sugestão de troca entre duas propriedades.
     *
     * @param p1 Primeira propriedade.
     * @param p2 Segunda propriedade.
     * @param score Valor numérico representando o benefício da troca.
     */
    public TrocaSugerida(Propriedade p1, Propriedade p2, double score) {
        this.p1 = p1;
        this.p2 = p2;
        this.score = score;
    }

    /**
     * Retorna o score da troca sugerida.
     *
     * @return O score da troca.
     */
    public double getScore() { return score; }

    /**
     * Retorna uma representação textual da troca sugerida.
     *
     * @return Uma string que contem os detalhes da troca e o score.
     */
    @Override
    public String toString() {
        return "Sugerir troca entre " + p1.getParId() + " (" + p1.getOwner() + ") ↔ " +
                p2.getParId() + " (" + p2.getOwner() + ") | Score: " + String.format("%.2f", score);
    }
}
