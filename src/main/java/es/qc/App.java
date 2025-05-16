package es.qc;

import org.locationtech.jts.io.ParseException;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

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

            // 3. Constrói o grafo de proprietários com base nas relações anteriores
            GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);

            System.out.println("\n=== Grafo de Proprietários ===");
            grafoProprietarios.mostrarGrafo();

            // 4. Visualização gráfica com GraphStream
            VisualizadorGrafoPropriedades.mostrar(grafoPropriedades);
            VisualizadorGrafoProprietarios.mostrar(grafoProprietarios);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Digite o tipo de localizacao (municipio, freguesia, ilha): ");
                String tipo = scanner.nextLine().trim();
                System.out.print("Digite o nome da localizacao: ");
                String localizacao = scanner.nextLine().trim();

                double media = grafoPropriedades.calcularAreaMediaPorLocalizacao(localizacao, tipo);
                System.out.printf("A área média das propriedades em %s (%s) é %.2f m²%n%n", localizacao, tipo, media);

                System.out.print("Deseja fazer outra consulta? (s/n): ");
                String resposta = scanner.nextLine().trim().toLowerCase();
                if (!resposta.equals("s")) {
                    System.out.println("Encerrando.");
                    break;
                }
            }


        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o CSV ou construir os grafos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
