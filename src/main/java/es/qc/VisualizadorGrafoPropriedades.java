package es.qc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.locationtech.jts.geom.Coordinate;

public class VisualizadorGrafoPropriedades {
    public static void mostrar(GrafoPropriedades grafo) {
        Graph graph = new SingleGraph("Mapa Cadastral");

        graph.setStrict(false);
        graph.setAutoCreate(true);

        // Calcula bounding box para normalização
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

        // Adiciona nós com coordenadas normalizadas
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

        // Adiciona arestas (linhas entre parcelas vizinhas), evitando duplicadas
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

        // Estilo dos nós e arestas
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

        graph.display();
    }
}
