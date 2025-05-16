package es.qc;

import java.util.*;

public class CalcularMedia {

    public static double calcularAreaMediaPorLocalizacao(Map<String, Propriedade> propriedades, String localizacao, String tipo) {
        double soma = 0;
        int contador = 0;

        for (Propriedade p : propriedades.values()) {
            boolean corresponde = switch (tipo.toLowerCase()) {
                case "freguesia" -> p.getFreguesia().equalsIgnoreCase(localizacao);
                case "municipio" -> p.getMunicipio().equalsIgnoreCase(localizacao);
                case "ilha" -> p.getIlha().equalsIgnoreCase(localizacao);
                default -> false;
            };

            if (corresponde) {
                soma += p.getArea();
                contador++;
            }
        }

        return contador > 0 ? soma / contador : 0;
    }

    /**
     * Calcula a área média agrupando propriedades adjacentes do mesmo dono numa região.
     */
    public static double calcularAreaMediaAgrupada(Map<String, Propriedade> propriedades, String tipoArea, String nome) {
        Set<String> visitados = new HashSet<>();
        List<Double> areasAgrupadas = new ArrayList<>();

        for (Propriedade prop : propriedades.values()) {
            if (visitados.contains(prop.getParId())) continue;

            boolean corresponde = false;
            String tipo = tipoArea.toLowerCase();
            if (tipo.equals("freguesia")) {
                corresponde = prop.getFreguesia().equalsIgnoreCase(nome);
            } else if (tipo.equals("municipio")) {
                corresponde = prop.getMunicipio().equalsIgnoreCase(nome);
            } else if (tipo.equals("ilha")) {
                corresponde = prop.getIlha().equalsIgnoreCase(nome);
            }

            if (!corresponde) continue;

            double areaGrupo = 0.0;
            String dono = prop.getOwner();
            Queue<Propriedade> fila = new LinkedList<>();
            fila.add(prop);
            visitados.add(prop.getParId());

            while (!fila.isEmpty()) {
                Propriedade atual = fila.poll();
                areaGrupo += atual.getArea();

                for (String vizinhoId : atual.getVizinhos()) {
                    Propriedade vizinho = propriedades.get(vizinhoId);
                    if (vizinho != null && !visitados.contains(vizinhoId)
                            && vizinho.getOwner().equals(dono)) {

                        boolean vizinhoCorresponde = false;
                        if (tipo.equals("freguesia")) {
                            vizinhoCorresponde = vizinho.getFreguesia().equalsIgnoreCase(nome);
                        } else if (tipo.equals("municipio")) {
                            vizinhoCorresponde = vizinho.getMunicipio().equalsIgnoreCase(nome);
                        } else if (tipo.equals("ilha")) {
                            vizinhoCorresponde = vizinho.getIlha().equalsIgnoreCase(nome);
                        }

                        if (vizinhoCorresponde) {
                            fila.add(vizinho);
                            visitados.add(vizinhoId);
                        }
                    }
                }
            }

            if (areaGrupo > 0) {
                areasAgrupadas.add(areaGrupo);
            }
        }

        double soma = 0.0;
        for (double a : areasAgrupadas) soma += a;
        return areasAgrupadas.isEmpty() ? 0.0 : soma / areasAgrupadas.size();
    }
}
