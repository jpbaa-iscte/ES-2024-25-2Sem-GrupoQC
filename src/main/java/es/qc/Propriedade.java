package es.qc;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Representa uma propriedade (parcela) com informações geométricas, administrativas e relacionais.
 * Cada propriedade pode ter vizinhos, um proprietário, localização administrativa e uma geometria.
 */
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
    private final Coordinate centroide;

    /**
     * Construtor da classe {@code Propriedade}.
     *
     * @param parId       identificador único da propriedade
     * @param parNum      número da parcela
     * @param comprimento perímetro ou comprimento da propriedade
     * @param area        área da propriedade
     * @param geometryWKT representação WKT da geometria
     * @param geometry    geometria espacial da propriedade
     * @param owner       nome do proprietário
     * @param freguesia   freguesia onde se localiza a propriedade
     * @param municipio   município onde se localiza a propriedade
     * @param ilha        ilha onde se localiza a propriedade
     */
    public Propriedade(String parId, String parNum, double comprimento, double area,
                       String geometryWKT, Geometry geometry, String owner,
                       String freguesia, String municipio, String ilha) {
        this.parId = parId;
        this.parNum = parNum;
        this.comprimento = comprimento;
        this.area = area;
        this.geometryWKT = geometryWKT;
        this.geometry = geometry;
        this.centroide = geometry != null ? geometry.getCentroid().getCoordinate() : null;
        this.owner = owner;
        this.freguesia = freguesia;
        this.municipio = municipio;
        this.ilha = ilha;
        this.vizinhos = new HashSet<>();
    }

    /**
     * Define um novo proprietário para a propriedade.
     * Este método é utilizado apenas em simulações de troca de propriedades.
     *
     * @param novoDono novo proprietário
     * @throws RuntimeException caso ocorra erro ao refletir o campo
     */
    public void setOwner(String novoDono) {
        try {
            java.lang.reflect.Field f = Propriedade.class.getDeclaredField("owner");
            f.setAccessible(true);
            f.set(this, novoDono);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao definir novo dono: " + e.getMessage());
        }
    }

    /**
     * @return identificador da propriedade
     */
    public String getParId() {
        return parId;
    }

    /**
     * @return geometria da propriedade
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Adiciona o ID de uma propriedade vizinha.
     *
     * @param vizinhoId identificador da propriedade vizinha
     */
    public void adicionarVizinho(String vizinhoId) {
        vizinhos.add(vizinhoId);
    }

    /**
     * @return conjunto de IDs das propriedades vizinhas
     */
    public Set<String> getVizinhos() {
        return vizinhos;
    }

    /**
     * @return nome do proprietário
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return área da propriedade
     */
    public double getArea() {
        return area;
    }

    /**
     * @return freguesia da propriedade
     */
    public String getFreguesia() {
        return freguesia;
    }

    /**
     * @return município da propriedade
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @return ilha da propriedade
     */
    public String getIlha() {
        return ilha;
    }

    /**
     * @return representação WKT da geometria
     */
    public String getGeometryWKT() {
        return geometryWKT;
    }

    /**
     * @return centroide da geometria da propriedade
     */
    public Coordinate getCentroide() {
        return centroide;
    }

    /**
     * @return comprimento (ou perímetro) da propriedade
     */
    public double getComprimento() {
        return comprimento;
    }

    /**
     * Representação textual da propriedade, incluindo localização e vizinhos.
     *
     * @return descrição textual da propriedade
     */
    @Override
    public String toString() {
        return "Parcela " + parId + " (" + municipio + " - " + freguesia + " - " + ilha +
                ") com vizinhos: " + vizinhos;
    }

    /**
     * Compara duas propriedades com base no seu {@code parId}.
     *
     * @param o objeto a comparar
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Propriedade)) return false;
        Propriedade that = (Propriedade) o;
        return Objects.equals(parId, that.parId);
    }

    /**
     * Gera código hash baseado no {@code parId}.
     *
     * @return código hash da propriedade
     */
    @Override
    public int hashCode() {
        return Objects.hash(parId);
    }
}