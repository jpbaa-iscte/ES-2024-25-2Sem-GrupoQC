package es.qc;

import org.locationtech.jts.io.ParseException;

import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String caminhoCSV = "Madeira-Moodle-1.1.csv/Madeira-Moodle-1.1.csv";

        try {
            // Carrega as propriedades a partir do CSV
            Map<String, Propriedade> propriedades = CSVLoader.carregarPropriedades(caminhoCSV);

            // Cria e constrói o grafo de propriedades
            GrafoPropriedades grafoPropriedades = new GrafoPropriedades();
            for (Propriedade prop : propriedades.values()) {
                grafoPropriedades.adicionarPropriedade(prop);
            }

            grafoPropriedades.construirGrafoAutomaticamente();

            System.out.println("=== Grafo de Propriedades ===");
            grafoPropriedades.mostrarGrafo();

            // Cria e mostra o grafo de proprietários (baseado no grafo anterior)
            GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);

            System.out.println("\n=== Grafo de Proprietários ===");
            grafoProprietarios.mostrarGrafo();

            // Mostrar graficamente os dois grafos
            VisualizadorGrafo.mostrarGrafoDeProprietarios(grafoProprietarios);
            VisualizadorGrafo.mostrarGrafoDePropriedades(grafoPropriedades);


        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o CSV ou construir os grafos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}