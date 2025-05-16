package es.qc;

import java.io.IOException;
import java.text.ParseException;
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

            // 3. Constrói o grafo de proprietários com base nas relações entre parcelas
            GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);

            System.out.println("\n=== Grafo de Proprietários ===");
            grafoProprietarios.mostrarGrafo();

            VisualizadorGrafoPropriedades.mostrar(grafoPropriedades);
            VisualizadorGrafoProprietarios.mostrar(grafoProprietarios,grafoPropriedades);

            // 4. Permitir calcular a área média das propriedades
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Calcular área média por região (Ponto 4)");
                System.out.println("2 - Calcular área média agrupada por região (Ponto 5)");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                String opcao = scanner.nextLine().trim();

                if (opcao.equals("0")) {
                    System.out.println("Encerrando.");
                    break;
                }

                System.out.print("Digite o tipo de localização (municipio, freguesia, ilha): ");
                String tipo = scanner.nextLine().trim();
                System.out.print("Digite o nome da localização: ");
                String localizacao = scanner.nextLine().trim();

                if (opcao.equals("1")) {
                    double media = grafoPropriedades.calcularAreaMediaPorLocalizacao(localizacao, tipo);
                    System.out.printf("A área média das propriedades em %s (%s) é %.2f m²%n", localizacao, tipo, media);
                } else if (opcao.equals("2")) {
                    double mediaAgrupada = CalcularMedia.calcularAreaMediaAgrupada(propriedades, tipo, localizacao);
                    System.out.printf("A área média AGRUPADA (propriedades adjacentes do mesmo dono) em %s (%s) é %.2f m²%n", localizacao, tipo, mediaAgrupada);
                } else {
                    System.out.println("Opção inválida.");
                }
            }

        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o CSV ou construir os grafos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
