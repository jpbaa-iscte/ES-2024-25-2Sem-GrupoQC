package es.qc;

import org.locationtech.jts.geom.Geometry;

import java.util.HashSet;
import java.util.Set;

public class Propriedade {
    private String parId;
    private String parNum;
    private double comprimento;
    private double area;
    private String geometryWKT;
    private Geometry geometry;
    private String owner;
    private String freguesia;
    private String municipio;
    private String ilha;
    private Set<String> vizinhos;

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

    @Override
    public String toString() {
        return "Parcela " + parId + " (" + municipio + " - " + freguesia + ") com vizinhos: " + vizinhos;
    }
}