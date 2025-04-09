package es.qc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void mostrarGrafo() {
        for (Propriedade prop : propriedades.values()) {
            System.out.println(prop);
        }
    }
}