package es.qc;

import java.util.*;

/**
 * Classe responsável por gerar sugestões de trocas de propriedades entre diferentes proprietários,
 * com o objetivo de aumentar a área média agrupada de propriedades adjacentes.
 *
 * <p>O algoritmo analisa pares de propriedades vizinhas de diferentes donos e calcula o ganho potencial
 * em termos de área agrupada caso essas propriedades fossem trocadas entre si.
 * Sugestões com maior ganho e menor diferença de área entre propriedades são priorizadas.
 */
public class GeradorSugestoesTroca {

    /**
     * Gera uma lista de sugestões de troca entre pares de propriedades vizinhas que pertencem a diferentes proprietários.
     * Apenas são sugeridas trocas que resultem num aumento da área agrupada.
     *
     * @param propriedades mapa contendo todas as propriedades (chave: ID da propriedade)
     * @return lista de sugestões de troca ordenadas por maior ganho e menor diferença de área
     */
    public static List<SugestaoTroca> sugerirTrocas(Map<String, Propriedade> propriedades) {
        List<SugestaoTroca> sugestoes = new ArrayList<>();
        Set<String> processados = new HashSet<>();

        for (Propriedade p1 : propriedades.values()) {
            for (String vizinhoId : p1.getVizinhos()) {
                Propriedade p2 = propriedades.get(vizinhoId);

                if (p2 == null || p1.getOwner().equals(p2.getOwner())) continue;

                String par = gerarParId(p1.getParId(), p2.getParId());
                if (processados.contains(par)) continue;
                processados.add(par);

                // Calcular ganho antes da troca
                double ganhoAntes = calcularAreaAgrupadaMedia(p1, propriedades) + calcularAreaAgrupadaMedia(p2, propriedades);

                // Simular troca
                simularTroca(p1, p2);
                double ganhoDepois = calcularAreaAgrupadaMedia(p1, propriedades) + calcularAreaAgrupadaMedia(p2, propriedades);
                desfazerTroca(p1, p2); // reverter troca

                double ganho = ganhoDepois - ganhoAntes;
                double diferencaArea = Math.abs(p1.getArea() - p2.getArea());

                if (ganho > 0) {
                    SugestaoTroca sugestao = new SugestaoTroca(p1, p2, ganho, diferencaArea);
                    sugestoes.add(sugestao);
                }
            }
        }

        // Ordena sugestões: maior ganho primeiro, menor diferença de área depois
        sugestoes.sort(Comparator
                .comparingDouble(SugestaoTroca::getGanhoTotal).reversed()
                .thenComparingDouble(SugestaoTroca::getDiferencaArea));

        return sugestoes;
    }

    /**
     * Gera uma string única para representar um par de propriedades, independentemente da ordem.
     *
     * @param a ID da primeira propriedade
     * @param b ID da segunda propriedade
     * @return identificador único do par
     */
    private static String gerarParId(String a, String b) {
        return a.compareTo(b) < 0 ? a + "_" + b : b + "_" + a;
    }

    /**
     * Troca temporariamente os donos de duas propriedades.
     *
     * @param p1 primeira propriedade
     * @param p2 segunda propriedade
     */
    private static void simularTroca(Propriedade p1, Propriedade p2) {
        String temp = p1.getOwner();
        p1.setOwner(p2.getOwner());
        p2.setOwner(temp);
    }

    /**
     * Reverte uma troca entre duas propriedades, restaurando os donos originais.
     *
     * @param p1 primeira propriedade
     * @param p2 segunda propriedade
     */
    private static void desfazerTroca(Propriedade p1, Propriedade p2) {
        simularTroca(p1, p2); // aplicar troca novamente desfaz a anterior
    }

    /**
     * Calcula a área total de um grupo de propriedades adjacentes pertencentes ao mesmo dono,
     * partindo da propriedade dada.
     *
     * @param p            propriedade inicial
     * @param propriedades mapa com todas as propriedades
     * @return soma das áreas do grupo conectado (componentes adjacentes com o mesmo dono)
     */
    private static double calcularAreaAgrupadaMedia(Propriedade p, Map<String, Propriedade> propriedades) {
        Set<String> visitados = new HashSet<>();
        Queue<Propriedade> fila = new LinkedList<>();
        fila.add(p);
        visitados.add(p.getParId());

        double totalArea = 0;

        while (!fila.isEmpty()) {
            Propriedade atual = fila.poll();
            totalArea += atual.getArea();

            for (String vizId : atual.getVizinhos()) {
                Propriedade vizinho = propriedades.get(vizId);
                if (vizinho != null && !visitados.contains(vizId) &&
                        vizinho.getOwner().equals(atual.getOwner())) {
                    fila.add(vizinho);
                    visitados.add(vizId);
                }
            }
        }

        return totalArea;
    }
}