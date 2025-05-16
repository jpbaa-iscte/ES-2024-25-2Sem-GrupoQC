package es.qc;

import org.locationtech.jts.geom.Geometry;
import java.util.*;

public class GrafoPropriedades {
    private final Map<String, Propriedade> propriedades;

    public GrafoPropriedades() {
        this.propriedades = new HashMap<>();
    }

    public void adicionarPropriedade(Propriedade prop) {
        propriedades.put(prop.getParId(), prop);
    }

    public Map<String, Propriedade> getPropriedades() {
        return propriedades;
    }

    public void construirGrafoAutomaticamente() {
        List<Propriedade> lista = new ArrayList<>(propriedades.values());

        for (int i = 0; i < lista.size(); i++) {
            Propriedade p1 = lista.get(i);
            Geometry g1 = p1.getGeometry();

            for (int j = i + 1; j < lista.size(); j++) {
                Propriedade p2 = lista.get(j);
                Geometry g2 = p2.getGeometry();

                if (g1.intersects(g2)) {
                    p1.adicionarVizinho(p2.getParId());
                    p2.adicionarVizinho(p1.getParId());
                }
            }
        }
    }

    public void mostrarGrafo() {
        for (Propriedade prop : propriedades.values()) {
            System.out.println(prop);
        }
    }
}