package es.qc;

import org.locationtech.jts.geom.Geometry;
import java.util.*;

/**
 * Representa um grafo, via shell, onde cada nó é uma {@link Propriedade}, e as arestas representam
 * a adjacência espacial entre propriedades.
 * <p>
 * Utilizando a biblioteca JTS para a verificação das interseções espaciais entre geometrias das propriedades.
 * O grafo é construído automaticamente com base na interseção (por borda ou ponto) das geometrias.
 *
 * <p>Uso típico:
 * <pre>
 *     GrafoPropriedades grafo = new GrafoPropriedades();
 *     grafo.adicionarPropriedade(...);
 *     grafo.construirGrafoAutomaticamente();
 *     grafo.mostrarGrafo();
 * </pre>
 */
public class GrafoPropriedades {
    private final Map<String, Propriedade> propriedades;

    /**
     * Construtor padrão. Inicializa o grafo vazio.
     */
    public GrafoPropriedades() {
        this.propriedades = new HashMap<>();
    }

    /**
     * Adiciona uma {@link Propriedade} ao grafo.
     *
     * @param prop A propriedade a ser adicionada.
     */
    public void adicionarPropriedade(Propriedade prop) {
        propriedades.put(prop.getParId(), prop);
    }

    /**
     * Retorna o mapa de propriedades do grafo.
     * A chave é o {@code parId} e o valor é o objeto {@link Propriedade}.
     *
     * @return Um {@code Map<String, Propriedade>} com todas as propriedades armazenadas.
     */
    public Map<String, Propriedade> getPropriedades() {
        return propriedades;
    }

    /**
     * Constrói automaticamente o grafo de vizinhança espacial.
     * <p>
     * Para cada par de propriedades, verifica se suas geometrias se intersectam.
     * Se houver interseção, registra ambas como vizinhas uma da outra.
     *
     * <p>Obs: Utiliza {@code Geometry.intersects()} para detectar vizinhança, o que inclui
     * toques por bordas ou vértices.
     */
    public void construirGrafoAutomaticamente() {
        List<Propriedade> lista = new ArrayList<>(propriedades.values());

        for (int i = 0; i < lista.size(); i++) {
            Propriedade p1 = lista.get(i);
            Geometry g1 = p1.getGeometry();

            for (int j = i + 1; j < lista.size(); j++) {
                Propriedade p2 = lista.get(j);
                Geometry g2 = p2.getGeometry();

                // Considera propriedades adjacentes pelas bordas (não sobrepostas)
                if (g1.intersects(g2)) {
                    p1.adicionarVizinho(p2.getParId());
                    p2.adicionarVizinho(p1.getParId());
                }
            }
        }
    }

    /**
     * Imprime no console todas as propriedades e suas respectivas vizinhanças.
     * <p>
     * Utiliza o método {@code toString()} da classe {@link Propriedade} para exibição.
     */
    public void mostrarGrafo() {
        for (Propriedade prop : propriedades.values()) {
            System.out.println(prop);
        }
    }
}


