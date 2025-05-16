package es.qc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VisualizadorGrafoProprietarios {

    public static void mostrar(GrafoProprietarios grafoProprietarios) {
        Graph graph = new SingleGraph("Grafo de Proprietários");

        // Adiciona nós
        for (String owner : grafoProprietarios.getGrafo().keySet()) {
            graph.addNode(owner).setAttribute("ui.label", owner);
        }

        // Adiciona arestas
        Set<String> arestasAdicionadas = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : grafoProprietarios.getGrafo().entrySet()) {
            String origem = entry.getKey();
            for (String destino : entry.getValue()) {
                String arestaId = origem.compareTo(destino) < 0 ? origem + "-" + destino : destino + "-" + origem;

                if (!arestasAdicionadas.contains(arestaId)) {
                    graph.addEdge(arestaId, origem, destino);
                    arestasAdicionadas.add(arestaId);
                }
            }
        }

        graph.setAttribute("ui.stylesheet", "node { fill-color: green; }");
        graph.display();
    }
}


