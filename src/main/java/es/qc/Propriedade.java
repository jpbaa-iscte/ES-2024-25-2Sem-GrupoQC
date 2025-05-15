package es.qc;

import org.locationtech.jts.geom.Geometry;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Propriedade} class represents a property with various attributes such as
 * identifier, geometry, owner, and neighboring properties.
 * <p>
 * This class provides methods to access property details, manage neighbors, and
 * represent the property as a string.
 * </p>
 *
 * <p><strong>Attributes:</strong></p>
 * <ul>
 *   <li>{@code parId} - Unique identifier for the property.</li>
 *   <li>{@code parNum} - Parcel number of the property.</li>
 *   <li>{@code comprimento} - Length of the property boundary.</li>
 *   <li>{@code area} - Area of the property.</li>
 *   <li>{@code geometryWKT} - Geometry in WKT (Well-Known Text) format.</li>
 *   <li>{@code geometry} - Geometry object representing the property.</li>
 *   <li>{@code owner} - Owner of the property.</li>
 *   <li>{@code freguesia} - Parish where the property is located.</li>
 *   <li>{@code municipio} - Municipality where the property is located.</li>
 *   <li>{@code ilha} - Island where the property is located.</li>
 *   <li>{@code vizinhos} - Set of neighboring property identifiers.</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * {@code
 * Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", geometry, "Owner", "Parish", "Municipality", "Island");
 * propriedade.adicionarVizinho("2");
 * }
 * </pre>
 *
 * @author jxbarbosax
 * @version 1.0
 * @since 2023-10-10
 */

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

    public String getOwner(){
        return owner;
    }

    @Override
    public String toString() {
        return "Parcela " + parId + " (" + municipio + " - " + freguesia  + " - " + ilha + ") com vizinhos: " + vizinhos;
    }
}