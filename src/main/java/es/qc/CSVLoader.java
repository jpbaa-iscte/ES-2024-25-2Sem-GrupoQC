package es.qc;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitária responsável por carregar dados a partir de um arquivo CSV.
 * <p>
 * Cada linha do CSV contém informações acerca da propriedades como identificadores, medidas (comprimento e área),
 * geometria WKT e metadados (dono, freguesia, município, ilha). A geometria é interpretada
 * usando a biblioteca JTS (Java Topology Suite).
 * <p>
 * O CSV deve ter o seguinte formato, com colunas separadas por ponto e vírgula (;):
 * <pre>
 * (cabeçalho)
 * (ignorado pela função)
 * id;parId;parNum;comprimento;area;geometryWKT;owner;freguesia;municipio;ilha
 * </pre>
 */
public class CSVLoader {

    /**
     * Carrega uma coleção de objetos {@link Propriedade} a partir de um arquivo CSV.
     *
     * @param caminhoCSV Caminho para o arquivo CSV contendo os dados das propriedades.
     * @return Um {@code Map<String, Propriedade>} onde a chave é o {@code parId} e o valor é o objeto {@code Propriedade}.
     * @throws IOException Se ocorrer um erro ao ler o arquivo.
     * @throws ParseException A sua existência deve-se a outro contexto.
     */
    public static Map<String, Propriedade> carregarPropriedades(String caminhoCSV) throws IOException, ParseException {
        Map<String, Propriedade> propriedades = new HashMap<>();
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))) {
            br.readLine(); // Pula o cabeçalho
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";", -1);
                if (partes.length < 10) continue;

                String parId = partes[1].trim();
                String parNum = partes[2].trim();

                double comprimento;
                double area;
                try {
                    comprimento = Double.parseDouble(partes[3].trim().replace(",", "."));
                    area = Double.parseDouble(partes[4].trim().replace(",", "."));
                } catch (NumberFormatException e) {
                    System.err.println("Número inválido na linha com parId " + parId + ", ignorando...");
                    continue;
                }

                String geometryWKT = partes[5].trim();
                String owner = partes[6].trim();
                String freguesia = partes[7].trim();
                String municipio = partes[8].trim();
                String ilha = partes[9].trim();

                Geometry geometry;
                try {
                    geometry = reader.read(geometryWKT);

                    if (geometry == null || geometry.isEmpty() || !geometry.isValid()) {
                        System.err.println("Geometria inválida para parId " + parId + ", ignorando...");
                        continue;
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao ler geometria WKT para parId " + parId + ": " + e.getMessage());
                    continue;
                }

                Propriedade prop = new Propriedade(parId, parNum, comprimento, area,
                        geometryWKT, geometry, owner,
                        freguesia, municipio, ilha);
                propriedades.put(parId, prop);
            }
        }

        return propriedades;
    }
}
