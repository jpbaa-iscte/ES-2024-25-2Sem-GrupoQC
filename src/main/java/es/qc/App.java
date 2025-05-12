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
            Map<String, Propriedade> propriedades = CSVLoader.carregarPropriedades(caminhoCSV);

            GrafoPropriedades grafo = new GrafoPropriedades();
            for (Propriedade prop : propriedades.values()) {
                grafo.adicionarPropriedade(prop);
            }

            grafo.construirGrafoAutomaticamente();
            grafo.mostrarGrafo();
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o CSV ou construir o grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

