package es.qc;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) {
        String caminhoCSV = "Madeira-Moodle-1.1.csv/Madeira-Moodle-1.1.csv";
        Map<String, Propriedade> propriedades = CSVLoader.carregarPropriedades(caminhoCSV);

        GrafoPropriedades grafo = new GrafoPropriedades();
        for (Propriedade prop : propriedades.values()) {
            grafo.adicionarPropriedade(prop);
        }

        // Construir grafo antes de mostrar
        grafo.construirGrafoAutomaticamente();

        grafo.mostrarGrafo();
    }
}
