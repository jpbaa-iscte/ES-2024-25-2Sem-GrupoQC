package es.qc;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Caminho do ficheiro CSV
        String caminhoCSV = "Madeira-Moodle-1.1.csv/Madeira-Moodle-1.1.csv";

        try {
            // 1. Carrega as propriedades a partir do CSV
            Map<String, Propriedade> propriedades = CSVLoader.carregarPropriedades(caminhoCSV);

            // 2. Constrói o grafo de propriedades (parcelas)
            GrafoPropriedades grafoPropriedades = new GrafoPropriedades();
            for (Propriedade prop : propriedades.values()) {
                grafoPropriedades.adicionarPropriedade(prop);
            }
            grafoPropriedades.construirGrafoAutomaticamente();

            System.out.println("=== Grafo de Propriedades ===");
            grafoPropriedades.mostrarGrafo();

            // 3. Constrói o grafo de proprietários com base nas relações entre parcelas
            GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);

            System.out.println("\n=== Grafo de Proprietários ===");
            grafoProprietarios.mostrarGrafo();

            // 4. Visualizações gráficas com GraphStream
            VisualizadorGrafoPropriedades.mostrar(grafoPropriedades);
            VisualizadorGrafoProprietarios.mostrar(grafoProprietarios,grafoPropriedades);

        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o CSV ou construir os grafos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
