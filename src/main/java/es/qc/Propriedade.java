package es.qc;

import org.locationtech.jts.geom.Geometry;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Propriedade {
    private final String parId;
    private final String parNum;
    private final double comprimento;
    private final double area;
    private final String geometryWKT;
    private final Geometry geometry;
    private final String owner;
    private final String freguesia;
    private final String municipio;
    private final String ilha;
    private final Set<String> vizinhos;

    public Propriedade(String parId, String parNum, double comprimento, double area,
                       String geometryWKT, Geometry geometry, String owner,
                       String freguesia, String municipio, String ilha) {
        this.parId = parId;
        this.parNum = parNum;
        this.comprimento = comprimento;
        this.area = area;
        this.geometryWKT = geometryWKT;
        this.geometry = geometry;
        this.owner = owner;
        this.freguesia = freguesia;
        this.municipio = municipio;
        this.ilha = ilha;
        this.vizinhos = new HashSet<>();
    }

    public String getParId() {
        return parId;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void adicionarVizinho(String vizinhoId) {
        vizinhos.add(vizinhoId);
    }

    public Set<String> getVizinhos() {
        return vizinhos;
    }

    public String getOwner() {
        return owner;
    }

    public String getFreguesia() {
        return freguesia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getIlha() {
        return ilha;
    }

    public double getArea() {
        return area;
    }


    public String getGeometryWKT() {
        return geometryWKT;
    }

    @Override
    public String toString() {
        return "Parcela " + parId + " (" + municipio + " - " + freguesia + " - " + ilha +
                ") com vizinhos: " + vizinhos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Propriedade)) return false;
        Propriedade that = (Propriedade) o;
        return Objects.equals(parId, that.parId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parId);
    }
}
