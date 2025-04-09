package es.qc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code GrafoPropriedades} class represents a graph structure where nodes
 * correspond to properties ({@link Propriedade}) and edges represent relationships
 * (e.g., adjacency) between these properties.
 * <p>
 * This class provides methods to add properties, establish relationships, and
 * perform graph-related operations such as traversal and querying.
 * </p>
 *
 * <p><strong>Attributes:</strong></p>
 * <ul>
 *   <li>{@code propriedades} - A map of property identifiers to {@link Propriedade} objects.</li>
 *   <li>{@code adjacencias} - A map representing adjacency lists for the graph.</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * {@code
 * GrafoPropriedades grafo = new GrafoPropriedades();
 * grafo.adicionarPropriedade(propriedade);
 * grafo.estabelecerAdjacencia("1", "2");
 * }
 * </pre>
 *
 * @author jxbarbosax
 * @version 1.0
 * @since 2023-10-10
 */

public class GrafoPropriedades {
    private Map<String, Propriedade> propriedades;

    public GrafoPropriedades() {
        this.propriedades = new HashMap<>();
    }

    public void adicionarPropriedade(Propriedade prop) {
        propriedades.put(prop.getParId(), prop);
    }

    public void construirGrafoAutomaticamente() {
        List<Propriedade> lista = new ArrayList<>(propriedades.values());

        for (int i = 0; i < lista.size(); i++) {
            Propriedade p1 = lista.get(i);
            for (int j = i + 1; j < lista.size(); j++) {
                Propriedade p2 = lista.get(j);

                // Verifica se há interseção (adjacência)
                if (p1.getGeometry().intersects(p2.getGeometry())) {
                    p1.adicionarVizinho(p2.getParId());
                    p2.adicionarVizinho(p1.getParId());
                }
            }
        }
    }

    public Map<String, Propriedade> getPropriedades() {
        return propriedades;
    }

    public void mostrarGrafo() {
        for (Propriedade prop : propriedades.values()) {
            System.out.println(prop);
        }
    }
}