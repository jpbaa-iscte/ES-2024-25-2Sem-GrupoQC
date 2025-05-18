package es.qc;

import java.util.*;

public class SugestorTrocas {

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

        // Penalização por diferença de áreas e comprimentos
        double diffArea = Math.abs(p1.getArea() - p2.getArea());
        double diffComprimento = Math.abs(p1.getComprimento() - p2.getComprimento());

        // Score final: ganho de área menos penalizações
        return ganhoAreaMedia - 0.1 * diffArea - 0.05 * diffComprimento;
    }
}

class TrocaSugerida {
    private final Propriedade p1, p2;
    private final double score;

    public TrocaSugerida(Propriedade p1, Propriedade p2, double score) {
        this.p1 = p1;
        this.p2 = p2;
        this.score = score;
    }

    public double getScore() { return score; }

    @Override
    public String toString() {
        return "Sugerir troca entre " + p1.getParId() + " (" + p1.getOwner() + ") ↔ " +
                p2.getParId() + " (" + p2.getOwner() + ") | Score: " + String.format("%.2f", score);
    }
}