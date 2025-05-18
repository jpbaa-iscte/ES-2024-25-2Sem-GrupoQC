package es.qc;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe principal da aplicação que carrega os dados do ficheiro CSV,
 * constrói os grafos de propriedades e de proprietários, e fornece
 * um menu interativo para realizar operações como cálculo de áreas médias
 * e sugestão de trocas de propriedades.
 *
 * <p>As funcionalidades incluem:
 * <ul>
 *   <li>Carregamento de propriedades a partir de um ficheiro CSV</li>
 *   <li>Construção de grafos com base em vizinhança e donos das parcelas</li>
 *   <li>Visualização dos grafos</li>
 *   <li>Cálculo da área média por região</li>
 *   <li>Cálculo da área média agrupando propriedades adjacentes do mesmo dono</li>
 *   <li>Geração de sugestões de troca de propriedades</li>
 * </ul>
 *
 * O caminho para o ficheiro CSV deve ser corretamente especificado na variável {@code caminhoCSV}.
 */
public class App {

    /**
     * Ponto de entrada principal da aplicação.
     *
     * @param args argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {
        // Caminho do ficheiro CSV contendo os dados das propriedades
        String caminhoCSV = "Madeira-Moodle-1.1.csv/Madeira-Moodle-1.1.csv";

        try {
            // 1. Carregamento dos dados
            Map<String, Propriedade> propriedades = CSVLoader.carregarPropriedades(caminhoCSV);

            // 2. Construção do grafo de propriedades (parcelas)
            GrafoPropriedades grafoPropriedades = new GrafoPropriedades();
            for (Propriedade prop : propriedades.values()) {
                grafoPropriedades.adicionarPropriedade(prop);
            }
            grafoPropriedades.construirGrafoAutomaticamente();

            System.out.println("=== Grafo de Propriedades ===");
            grafoPropriedades.mostrarGrafo();

            // 3. Construção do grafo de proprietários com base nas adjacências
            GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);
            System.out.println("\n=== Grafo de Proprietários ===");
            grafoProprietarios.mostrarGrafo();

            // 4. Visualização dos grafos (opcional)
            VisualizadorGrafoPropriedades.mostrar(grafoPropriedades);
            VisualizadorGrafoProprietarios.mostrar(grafoProprietarios, grafoPropriedades);

            // 5. Menu interativo
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Calcular área média por região (Ponto 4)");
                System.out.println("2 - Calcular área média agrupada por região (Ponto 5)");
                System.out.println("3 - Sugerir trocas entre propriedades (Ponto 6)");
                System.out.println("4 - Sugerir trocas entre propriedades (Ponto 7)");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                String opcao = scanner.nextLine().trim();

                if (opcao.equals("0")) {
                    System.out.println("Encerrando.");
                    break;
                }

                // Input comum às opções 1 e 2
                System.out.print("Digite o tipo de localização (municipio, freguesia, ilha): ");
                String tipo = scanner.nextLine().trim();
                System.out.print("Digite o nome da localização: ");
                String localizacao = scanner.nextLine().trim();

                switch (opcao) {
                    case "1" -> {
                        // Cálculo simples da média das áreas por localização
                        double media = CalcularMedia.calcularAreaMediaPorLocalizacao(propriedades, localizacao, tipo);
                        System.out.printf("A área média das propriedades em %s (%s) é %.2f m²%n", localizacao, tipo, media);
                    }
                    case "2" -> {
                        // Cálculo da média considerando agrupamento de propriedades adjacentes do mesmo dono
                        double mediaAgrupada = CalcularMedia.calcularAreaMediaAgrupada(propriedades, tipo, localizacao);
                        System.out.printf("A área média AGRUPADA (propriedades adjacentes do mesmo dono) em %s (%s) é %.2f m²%n", localizacao, tipo, mediaAgrupada);
                    }
                    case "3" -> {
                        // Sugestão de trocas baseadas em área e proximidade (modelo simples)
                        List<SugestaoTroca> sugestoes = GeradorSugestoesTroca.sugerirTrocas(propriedades);
                        System.out.println("\n=== Sugestões de Trocas ===");
                        for (int i = 0; i < Math.min(10, sugestoes.size()); i++) {
                            System.out.println((i + 1) + ". " + sugestoes.get(i));
                        }
                    }
                    case "4" -> {
                        // Sugestões mais realistas de troca com base em área agrupada, comprimento e vizinhança
                        System.out.println("A sugerir trocas de propriedades...");
                        List<TrocaSugerida> trocas = SugestorTrocas.sugerirTrocas(propriedades);
                        if (trocas.isEmpty()) {
                            System.out.println("Nenhuma troca sugerida.");
                        } else {
                            for (TrocaSugerida troca : trocas) {
                                System.out.println(troca);
                            }
                        }
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }

        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o CSV ou construir os grafos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}