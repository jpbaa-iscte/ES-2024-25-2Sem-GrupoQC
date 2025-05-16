package es.qc;

import java.util.*;

public class GrafoProprietarios {
    private final Map<String, Set<String>> grafo; // chave: proprietário, valor: conjunto de vizinhos

    public GrafoProprietarios(GrafoPropriedades grafoPropriedades) {
        this.grafo = new HashMap<>();
        construirGrafoDeProprietarios(grafoPropriedades);
    }

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

    public Map<String, Set<String>> getGrafo() {
        return grafo;
    }

    public void mostrarGrafo() {
        System.out.println("===== Grafo de Proprietários =====");
        List<String> proprietariosOrdenados = new ArrayList<>(grafo.keySet());
        Collections.sort(proprietariosOrdenados);

        for (String proprietario : proprietariosOrdenados) {
            Set<String> vizinhos = grafo.get(proprietario);
            List<String> vizinhosOrdenados = new ArrayList<>(vizinhos);
            Collections.sort(vizinhosOrdenados);

            System.out.println("Proprietário: " + proprietario);
            System.out.println("  ↳ Vizinhos: " + (vizinhosOrdenados.isEmpty() ? "Nenhum" : String.join(", ", vizinhosOrdenados)));
        }
    }
}
