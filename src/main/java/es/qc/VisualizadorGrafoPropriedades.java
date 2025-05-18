package es.qc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.locationtech.jts.geom.Coordinate;

/**
 * Classe utilitária para visualização gráfica do grafo de propriedades
 * utilizando a biblioteca GraphStream.
 * <p>
 * Cada propriedade é representada por um nó, posicionado de acordo com
 * o centroide da geometria correspondente, com coordenadas normalizadas
 * para melhor exibição.
 * <p>
 * As arestas representam vizinhanças entre propriedades — ou seja,
 * conexões entre parcelas cujas geometrias são adjacentes.
 *
 * <p>Uso típico:
 * <pre>{@code
 *     GrafoPropriedades grafo = ...;
 *     VisualizadorGrafoPropriedades.mostrar(grafo);
 * }</pre>
 */
public class VisualizadorGrafoPropriedades {

    /**
     * Visualiza um {@link GrafoPropriedades} como um grafo 2D interativo.
     * <p>
     * Cada nó representa uma propriedade, usando seu {@code parId} como rótulo.
     * As posições são calculadas com base no centroide de cada geometria, escaladas
     * para um canvas de 1000x1000. As arestas são criadas entre propriedades vizinhas.
     *
     * @param grafo O grafo de propriedades a ser visualizado.
     */
    public static void mostrar(GrafoPropriedades grafo) {
        Graph graph = new SingleGraph("Mapa Cadastral");

        graph.setStrict(false);
        graph.setAutoCreate(true);

        // Calcula bounding box para normalização das coordenadas
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

        for (Propriedade p : grafo.getPropriedades().values()) {
            Coordinate c = p.getCentroide();
            if (c == null) continue;
            if (c.x < minX) minX = c.x;
            if (c.y < minY) minY = c.y;
            if (c.x > maxX) maxX = c.x;
            if (c.y > maxY) maxY = c.y;
        }

        double scaleX = 1000.0 / (maxX - minX);
        double scaleY = 1000.0 / (maxY - minY);

        // Criação dos nós com coordenadas normalizadas
        for (Propriedade p : grafo.getPropriedades().values()) {
            Coordinate c = p.getCentroide();
            if (c == null) continue;

            String id = p.getParId();
            Node node = graph.addNode(id);

            double x = (c.x - minX) * scaleX;
            double y = (c.y - minY) * scaleY;

            node.setAttribute("xy", x, y);
            node.setAttribute("ui.label", id);
        }

        // Criação das arestas entre propriedades vizinhas (evitando duplicações)
        for (Propriedade p : grafo.getPropriedades().values()) {
            for (String vizinhoId : p.getVizinhos()) {
                String id1 = p.getParId();
                String id2 = vizinhoId;
                String edgeId = id1 + "-" + id2;

                if (graph.getEdge(edgeId) == null && graph.getEdge(id2 + "-" + id1) == null) {
                    graph.addEdge(edgeId, id1, id2);
                }
            }
        }

        // Estilização visual do grafo (nós e arestas)
        graph.setAttribute("ui.stylesheet",
                "node {" +
                        "size: 8px;" +
                        "fill-color: blue;" +
                        "text-size: 12;" +
                        "text-alignment: above;" +
                        "}" +
                        "edge {" +
                        "fill-color: gray;" +
                        "}"
        );

        // Exibição da janela gráfica
        graph.display();
    }
}

