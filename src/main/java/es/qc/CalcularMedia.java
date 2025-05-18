package es.qc;

import java.util.*;

/**
 * Classe utilitária que fornece métodos para calcular métricas estatísticas sobre propriedades,
 * nomeadamente a área média por localização geográfica e a área média agrupada
 * por conjuntos de propriedades adjacentes pertencentes ao mesmo proprietário.
 */
public class CalcularMedia {

    /**
     * Calcula a área média das propriedades localizadas numa determinada região
     * (freguesia, município ou ilha), independentemente da adjacência ou do proprietário.
     *
     * @param propriedades mapa de propriedades, onde a chave é o ID da propriedade
     * @param localizacao  nome da localização (freguesia, município ou ilha)
     * @param tipo         tipo de localização: "freguesia", "municipio" ou "ilha"
     * @return a área média das propriedades nessa localização; 0 se não houver propriedades
     */
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
     * Calcula a área média agrupada das propriedades numa determinada localização,
     * considerando agrupamentos de propriedades adjacentes que pertencem ao mesmo dono.
     *
     * <p>Do ponto de vista funcional, cada grupo de propriedades com o mesmo proprietário
     * é tratado como uma única "super propriedade", cuja área total é usada para o cálculo da média.
     *
     * @param propriedades mapa de propriedades, onde a chave é o ID da propriedade
     * @param tipoArea     tipo de localização: "ilha", "municipio" ou "freguesia"
     * @param valorArea    nome da localização
     * @return a área média dos grupos de propriedades adjacentes do mesmo dono; 0 se não houver grupos
     */
    public static double calcularAreaMediaAgrupada(Map<String, Propriedade> propriedades, String tipoArea, String valorArea) {
        Set<String> visitados = new HashSet<>();
        List<Double> areasAgrupadas = new ArrayList<>();

        for (Propriedade prop : propriedades.values()) {
            // Filtrar pela área administrativa indicada
            if (!correspondeAreaAdministrativa(prop, tipoArea, valorArea)) continue;

            if (!visitados.contains(prop.getParId())) {
                // Iniciar BFS para agrupar propriedades do mesmo dono e adjacentes
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

        return areasAgrupadas.isEmpty() ? 0.0 :
                areasAgrupadas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Verifica se uma propriedade pertence à localização geográfica especificada.
     *
     * @param prop       a propriedade a verificar
     * @param tipoArea   o tipo de localização: "ilha", "municipio" ou "freguesia"
     * @param valorArea  o nome da localização
     * @return {@code true} se a propriedade estiver na localização; {@code false} caso contrário
     */
    private static boolean correspondeAreaAdministrativa(Propriedade prop, String tipoArea, String valorArea) {
        return switch (tipoArea.toLowerCase()) {
            case "ilha" -> prop.getIlha().equalsIgnoreCase(valorArea);
            case "municipio" -> prop.getMunicipio().equalsIgnoreCase(valorArea);
            case "freguesia" -> prop.getFreguesia().equalsIgnoreCase(valorArea);
            default -> false;
        };
    }

}