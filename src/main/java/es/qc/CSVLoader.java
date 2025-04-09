package es.qc;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.geom.GeometryFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code CSVLoader} class is responsible for loading and processing CSV files
 * to extract property data and convert it into a usable format.
 * <p>
 * This class provides methods to load properties from a CSV file and return them
 * as a map of property identifiers to {@link Propriedade} objects.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * {@code
 * Map<String, Propriedade> propriedades = CSVLoader.carregarPropriedades("file.csv");
 * }
 * </pre>
 *
 * <p><strong>Dependencies:</strong></p>
 * This class depends on the following:
 * <ul>
 *   <li>{@link Propriedade} - Represents individual property data.</li>
 *   <li>External libraries for CSV parsing (if applicable).</li>
 * </ul>
 *
 * @author jxbarbosax
 * @version 1.0
 * @since 2023-10-10
 */

public class CSVLoader {

    public static Map<String, Propriedade> carregarPropriedades(String caminhoCSV) {
        Map<String, Propriedade> propriedades = new HashMap<>();
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))) {
            br.readLine(); // Cabeçalho
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";", -1);
                if (partes.length < 10) continue;

                String parId = partes[1].trim();
                String parNum = partes[2].trim();
                double comprimento = Double.parseDouble(partes[3].trim().replace(",", "."));
                double area = Double.parseDouble(partes[4].trim().replace(",", "."));
                String geometryWKT = partes[5].trim();
                String owner = partes[6].trim();
                String freguesia = partes[7].trim();
                String municipio = partes[8].trim();
                String ilha = partes[9].trim();

                Geometry geometry = reader.read(geometryWKT);
                Propriedade prop = new Propriedade(parId, parNum, comprimento, area,
                        geometryWKT, geometry, owner,
                        freguesia, municipio, ilha);
                propriedades.put(parId, prop);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return propriedades;
    }
}