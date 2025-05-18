package es.qc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.locationtech.jts.geom.Coordinate;

import java.util.*;

/**
 * Classe responsável por visualizar o grafo de proprietários de propriedades
 * com base nas suas localizações geográficas e vizinhanças.
 * <p>
 * Utiliza a biblioteca GraphStream para renderizar o grafo.
 */
public class VisualizadorGrafoProprietarios {

    /**
     * Exibe visualmente um grafo onde cada nó representa uma propriedade e cada aresta representa
     * uma relação de vizinhança entre propriedades de diferentes proprietários.
     * <p>
     * Os nós são posicionados com base nos centroides das geometrias das propriedades.
     *
     * @param grafoProprietarios Objeto que representa o grafo lógico de proprietários (não usado diretamente aqui, mas pode ser necessário para versões futuras).
     * @param grafoPropriedades  Contém todas as propriedades e suas relações de vizinhança.
     */
    public static void mostrar(GrafoProprietarios grafoProprietarios, GrafoPropriedades grafoPropriedades) {
        Graph graph = new SingleGraph("Mapa Cadastral de Proprietários");

        graph.setStrict(false);
        graph.setAutoCreate(true);

        // Cálculo do bounding box para normalizar as coordenadas
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

        // Criação dos nós com rótulo do proprietário
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

        // Criação das arestas entre propriedades de diferentes donos
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

        // Aplicação do estilo visual
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: green; size: 6px; text-size: 10; text-alignment: above; }" +
                        "edge { fill-color: gray; }");

        // Exibição do grafo
        graph.display();
    }
}