package es.qc;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashSet;
import java.util.Set;

public class VisualizadorGrafoPropriedades {

    public static void mostrar(GrafoPropriedades grafoPropriedades) {
        Graph graph = new SingleGraph("Grafo de Propriedades");

        // Adiciona nós
        for (Propriedade prop : grafoPropriedades.getPropriedades().values()) {
            graph.addNode(prop.getParId()).setAttribute("ui.label", prop.getParId());
        }

        // Adiciona arestas (bidirecionais mas sem duplicação)
        Set<String> arestasAdicionadas = new HashSet<>();
        for (Propriedade prop : grafoPropriedades.getPropriedades().values()) {
            for (String vizinhoId : prop.getVizinhos()) {
                String id1 = prop.getParId();
                String id2 = vizinhoId;
                String arestaId = id1.compareTo(id2) < 0 ? id1 + "-" + id2 : id2 + "-" + id1;

                if (!arestasAdicionadas.contains(arestaId)) {
                    graph.addEdge(arestaId, id1, id2);
                    arestasAdicionadas.add(arestaId);
                }
            }
        }

        graph.setAttribute("ui.stylesheet", "node { fill-color: blue; }");
        graph.display();
    }
}

