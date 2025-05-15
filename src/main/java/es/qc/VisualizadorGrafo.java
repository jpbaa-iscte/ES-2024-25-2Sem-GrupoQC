package es.qc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Map;
import java.util.Set;

public class VisualizadorGrafo {
    public static void mostrarGrafoDeProprietarios(GrafoProprietarios grafoProprietarios) {
        Graph graph = new SingleGraph("Grafo de Proprietários");

        // Estilo visual (opcional)
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: blue; size: 20px; text-size: 16px; text-alignment: above; } " +
                        "edge { fill-color: gray; }"
        );
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        // Adiciona nós
        for (String proprietario : grafoProprietarios.getGrafo().keySet()) {
            Node node = graph.addNode(proprietario);
            node.setAttribute("ui.label", proprietario);
        }

        // Adiciona arestas (sem duplicar)
        for (Map.Entry<String, Set<String>> entry : grafoProprietarios.getGrafo().entrySet()) {
            String origem = entry.getKey();
            for (String destino : entry.getValue()) {
                String edgeId = origem + "-" + destino;
                String reverseId = destino + "-" + origem;
                if (graph.getEdge(edgeId) == null && graph.getEdge(reverseId) == null) {
                    graph.addEdge(edgeId, origem, destino);
                }
            }
        }

        graph.display();
    }
    public static void mostrarGrafoDePropriedades(GrafoPropriedades grafoPropriedades) {
        Graph graph = new SingleGraph("Grafo de Propriedades");

        Map<String, Propriedade> propriedades = grafoPropriedades.getPropriedades();

        for (Propriedade p : propriedades.values()) {
            graph.addNode(p.getParId()).setAttribute("ui.label", p.getParId());
        }

        for (Propriedade p : propriedades.values()) {
            for (String vizinhoId : p.getVizinhos()) {
                String edgeId = p.getParId() + "-" + vizinhoId;
                String reverseId = vizinhoId + "-" + p.getParId();
                if (graph.getEdge(edgeId) == null && graph.getEdge(reverseId) == null) {
                    graph.addEdge(edgeId, p.getParId(), vizinhoId);
                }
            }
        }

        graph.display();
    }

}

