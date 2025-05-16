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

                // Considera propriedades adjacentes pelas bordas (não sobrepostas)
                if (g1.touches(g2)) {
                    p1.adicionarVizinho(p2.getParId());
                    p2.adicionarVizinho(p1.getParId());
                }
            }
        }
    }

    public double calcularAreaMediaPorLocalizacao(String localizacao, String tipo) {
        double soma = 0;
        int contador = 0;

        for (Propriedade p : propriedades.values()) {
            boolean corresponde = switch (tipo.toLowerCase()) {
                case "freguesia" -> p.getFreguesia().equalsIgnoreCase(localizacao);
                case "municipio" -> p.getMunicipio().equalsIgnoreCase(localizacao);
                case "ilha" -> p.getIlha().equalsIgnoreCase(localizacao);
                default -> false;
            };

            if (corresponde) {
                soma += p.getArea();
                contador++;
            }
        }

        return contador > 0 ? soma / contador : 0;
    }

    public void mostrarGrafo() {
        for (Propriedade prop : propriedades.values()) {
            System.out.println(prop);
        }
    }
}

