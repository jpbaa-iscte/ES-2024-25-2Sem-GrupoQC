package es.qc;

import java.util.*;

public class GeradorSugestoesTroca {

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

                // Simular troca
                double ganhoAntes = calcularAreaAgrupadaMedia(p1, propriedades) + calcularAreaAgrupadaMedia(p2, propriedades);

                simularTroca(p1, p2);
                double ganhoDepois = calcularAreaAgrupadaMedia(p1, propriedades) + calcularAreaAgrupadaMedia(p2, propriedades);
                desfazerTroca(p1, p2); // voltar ao estado original

                double ganho = ganhoDepois - ganhoAntes;
                double diferencaArea = Math.abs(p1.getArea() - p2.getArea());

                if (ganho > 0) {
                    SugestaoTroca sugestao = new SugestaoTroca(p1, p2, ganho, diferencaArea);
                    sugestoes.add(sugestao);
                }
            }
        }

        // Ordenar: primeiro por maior ganho, depois por menor diferença de área
        sugestoes.sort(Comparator
                .comparingDouble(SugestaoTroca::getGanhoTotal).reversed()
                .thenComparingDouble(SugestaoTroca::getDiferencaArea));

        return sugestoes;
    }

    private static String gerarParId(String a, String b) {
        return a.compareTo(b) < 0 ? a + "_" + b : b + "_" + a;
    }

    private static void simularTroca(Propriedade p1, Propriedade p2) {
        String temp = p1.getOwner();
        p1.setOwner(p2.getOwner());
        p2.setOwner(temp);
    }

    private static void desfazerTroca(Propriedade p1, Propriedade p2) {
        simularTroca(p1, p2); // troca de novo
    }

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

        return totalArea; // uma única "componente"
    }
}