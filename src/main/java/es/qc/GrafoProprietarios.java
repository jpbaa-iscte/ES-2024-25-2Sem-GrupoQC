package es.qc;

import java.util.*;

/**
 * Representa um grafo de vizinhança entre proprietários, via shell.
 * <p>
 * Cada nó no grafo é um proprietário, e existe uma aresta entre dois proprietários
 * se suas respectivas propriedades forem vizinhas (i.e., suas geometrias se tocarem).
 * <p>
 * O grafo é construído a partir de um {@link GrafoPropriedades}, agrupando as propriedades
 * por proprietário e estabelecendo conexões apenas entre donos distintos.
 *
 * <p>Uso típico:
 * <pre>
 *     GrafoProprietarios grafo = new GrafoProprietarios(grafoPropriedades);
 *     grafo.mostrarGrafo();
 * </pre>
 */
public class GrafoProprietarios {
    private final Map<String, Set<String>> grafo; // chave: proprietário, valor: conjunto de vizinhos

    /**
     * Construtor. Inicializa o grafo de proprietários com base no grafo de propriedades.
     *
     * @param grafoPropriedades Grafo contendo as propriedades e suas relações de vizinhança.
     */
    public GrafoProprietarios(GrafoPropriedades grafoPropriedades) {
        this.grafo = new HashMap<>();
        construirGrafoDeProprietarios(grafoPropriedades);
    }

    /**
     * Constrói o grafo de proprietários a partir do grafo de propriedades.
     * <p>
     * Para cada propriedade, verifica os seus vizinhos e cria arestas entre os
     * respectivos proprietários, apenas se forem diferentes.
     *
     * @param grafoPropriedades Grafo de propriedades previamente construído.
     */
    private void construirGrafoDeProprietarios(GrafoPropriedades grafoPropriedades) {
        Map<String, Propriedade> propriedades = grafoPropriedades.getPropriedades();

        for (Propriedade propriedade : propriedades.values()) {
            String proprietarioOrigem = propriedade.getOwner();
            grafo.putIfAbsent(proprietarioOrigem, new HashSet<>());

            for (String vizinhoId : propriedade.getVizinhos()) {
                Propriedade vizinha = propriedades.get(vizinhoId);
                if (vizinha == null) continue; // segurança extra

                String proprietarioVizinho = vizinha.getOwner();

                // Adiciona ligação apenas se forem donos diferentes
                if (!proprietarioOrigem.equals(proprietarioVizinho)) {
                    grafo.get(proprietarioOrigem).add(proprietarioVizinho);
                    grafo.putIfAbsent(proprietarioVizinho, new HashSet<>());
                    grafo.get(proprietarioVizinho).add(proprietarioOrigem);
                }
            }
        }
    }

    /**
     * Retorna o grafo de proprietários.
     *
     * @return Um {@code Map<String, Set<String>>} onde a chave é o nome de um proprietário
     *         e o valor é o conjunto de nomes de proprietários vizinhos.
     */
    public Map<String, Set<String>> getGrafo() {
        return grafo;
    }

    /**
     * Imprime o grafo de proprietários no console de forma legível.
     * <p>
     * Os proprietários e seus vizinhos são listados em ordem alfabética.
     */
    public void mostrarGrafo() {
        System.out.println("===== Grafo de Proprietários =====");
        List<String> proprietariosOrdenados = new ArrayList<>(grafo.keySet());
        Collections.sort(proprietariosOrdenados);

        for (String proprietario : proprietariosOrdenados) {
            Set<String> vizinhos = grafo.get(proprietario);
            List<String> vizinhosOrdenados = new ArrayList<>(vizinhos);
            Collections.sort(vizinhosOrdenados);

            System.out.println("Proprietário: " + proprietario);
            System.out.println("  | Vizinhos: " + (vizinhosOrdenados.isEmpty() ? "Nenhum" : String.join(", ", vizinhosOrdenados)));
        }
    }
}

