package es.qc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GrafoProprietarios {
    private Map<String, Set<String>> grafo; // chave: proprietário, valor: conjunto de vizinhos

    public GrafoProprietarios(GrafoPropriedades grafoPropriedades) {
        this.grafo = new HashMap<>();
        construirGrafoDeProprietarios(grafoPropriedades);
    }

    private void construirGrafoDeProprietarios(GrafoPropriedades grafoPropriedades) {
        Map<String, Propriedade> propriedades = grafoPropriedades.getPropriedades();

        for (Propriedade prop : propriedades.values()) {
            String proprietarioAtual = prop.getOwner();
            grafo.putIfAbsent(proprietarioAtual, new HashSet<>());

            for (String vizinhoId : prop.getVizinhos()) {
                Propriedade vizinha = propriedades.get(vizinhoId);
                String proprietarioVizinho = vizinha.getOwner();

                // Só adiciona se forem proprietários diferentes
                if (!proprietarioAtual.equals(proprietarioVizinho)) {
                    grafo.get(proprietarioAtual).add(proprietarioVizinho);
                    grafo.putIfAbsent(proprietarioVizinho, new HashSet<>());
                    grafo.get(proprietarioVizinho).add(proprietarioAtual);
                }
            }
        }
    }

    public Map<String, Set<String>> getGrafo() {
        return grafo;
    }

    public void mostrarGrafo() {
        for (Map.Entry<String, Set<String>> entry : grafo.entrySet()) {
            System.out.println("Proprietário " + entry.getKey() + " é vizinho de: " + entry.getValue());
        }
    }
}