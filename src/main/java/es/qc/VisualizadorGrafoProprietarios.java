package es.qc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.locationtech.jts.geom.Coordinate;

import java.util.*;

public class VisualizadorGrafoProprietarios {

    public static void mostrar(GrafoProprietarios grafoProprietarios, GrafoPropriedades grafoPropriedades) {
        Graph graph = new SingleGraph("Mapa Cadastral de Proprietários");

        graph.setStrict(false);
        graph.setAutoCreate(true);

        // Bounding box para normalizar coordenadas
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

        for (Propriedade p : grafoPropriedades.getPropriedades().values()) {
            Coordinate c = p.getCentroide();
            if (c == null) continue;

            if (c.x < minX) minX = c.x;
            if (c.y < minY) minY = c.y;
            if (c.x > maxX) maxX = c.x;
            if (c.y > maxY) maxY = c.y;
        }

        double scaleX = 1000.0 / (maxX - minX);
        double scaleY = 1000.0 / (maxY - minY);

        // Adiciona um nó por propriedade (com nome do owner como label)
        for (Propriedade p : grafoPropriedades.getPropriedades().values()) {
            Coordinate c = p.getCentroide();
            if (c == null) continue;

            String id = p.getParId();
            String owner = p.getOwner();
            Node node = graph.addNode(id);

            double x = (c.x - minX) * scaleX;
            double y = (c.y - minY) * scaleY;

            node.setAttribute("xy", x, y);
            node.setAttribute("ui.label", owner);
        }

        // Arestas apenas entre propriedades de owners diferentes
        Set<String> arestasAdicionadas = new HashSet<>();
        Map<String, Propriedade> propriedades = grafoPropriedades.getPropriedades();

        for (Propriedade p : propriedades.values()) {
            String id1 = p.getParId();
            String owner1 = p.getOwner();

            for (String vizinhoId : p.getVizinhos()) {
                Propriedade vizinha = propriedades.get(vizinhoId);
                if (vizinha == null) continue;

                String owner2 = vizinha.getOwner();

                if (!owner1.equals(owner2)) {
                    String edgeId = id1 + "-" + vizinhoId;
                    String reverseId = vizinhoId + "-" + id1;

                    if (!arestasAdicionadas.contains(edgeId) && !arestasAdicionadas.contains(reverseId)) {
                        graph.addEdge(edgeId, id1, vizinhoId);
                        arestasAdicionadas.add(edgeId);
                    }
                }
            }
        }

        // Estilo visual com labels visíveis
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: green; size: 6px; text-size: 10; text-alignment: above; }" +
                        "edge { fill-color: gray; }");

        graph.display();
    }
}
