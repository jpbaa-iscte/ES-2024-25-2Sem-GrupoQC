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
    public static double calcularAreaMediaAgrupada(Map<String, Propriedade> propriedades, String tipoArea, String valorArea) {
        Set<String> visitados = new HashSet<>();
        List<Double> areasAgrupadas = new ArrayList<>();

        for (Propriedade prop : propriedades.values()) {
            // Filtrar pela área administrativa indicada
            if (!correspondeAreaAdministrativa(prop, tipoArea, valorArea)) continue;

            if (!visitados.contains(prop.getParId())) {
                // Iniciar DFS/BFS para agrupar propriedades do mesmo dono e adjacentes
                double areaGrupo = 0.0;
                Queue<Propriedade> fila = new LinkedList<>();
                fila.add(prop);
                visitados.add(prop.getParId());

                while (!fila.isEmpty()) {
                    Propriedade atual = fila.poll();
                    areaGrupo += atual.getArea();

                    for (String vizinhoId : atual.getVizinhos()) {
                        Propriedade vizinho = propriedades.get(vizinhoId);
                        if (vizinho != null && !visitados.contains(vizinhoId)
                                && vizinho.getOwner().equals(atual.getOwner())
                                && correspondeAreaAdministrativa(vizinho, tipoArea, valorArea)) {

                            fila.add(vizinho);
                            visitados.add(vizinhoId);
                        }
                    }
                }
                areasAgrupadas.add(areaGrupo);
            }
        }

        // Calcular média
        return areasAgrupadas.isEmpty() ? 0.0 :
                areasAgrupadas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    // Método auxiliar para verificar a área geográfica
    private static boolean correspondeAreaAdministrativa(Propriedade prop, String tipoArea, String valorArea) {
        return switch (tipoArea.toLowerCase()) {
            case "ilha" -> prop.getIlha().equalsIgnoreCase(valorArea);
            case "municipio" -> prop.getMunicipio().equalsIgnoreCase(valorArea);
            case "freguesia" -> prop.getFreguesia().equalsIgnoreCase(valorArea);
            default -> false;
        };
    }

}
