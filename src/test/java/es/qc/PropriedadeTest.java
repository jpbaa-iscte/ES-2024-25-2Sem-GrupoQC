package es.qc;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for {@link Propriedade}.
 * <p>
 * Author: jxbarbosax
 * Date: 2023-10-10
 * </p>
 * <p>
 * Cyclomatic Complexity (CC) for each method:
 * <ul>
 *     <li>Constructor: CC = 1</li>
 *     <li>getParId: CC = 1</li>
 *     <li>getGeometry: CC = 1</li>
 *     <li>adicionarVizinho: CC = 1</li>
 *     <li>getVizinhos: CC = 1</li>
 *     <li>toString: CC = 1</li>
 * </ul>
 */
class PropriedadeTest {

    @Test
    void constructor() {
        // Test that the Propriedade object is created successfully
        Geometry geometry = new GeometryFactory().createPoint();
        assertDoesNotThrow(() -> new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", geometry, "Owner", "Parish", "Municipality", "Island"),
                "The constructor should not throw any exceptions.");
    }

    @Test
    void getParId() {
        // Test that the parId is returned correctly
        Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", null, "Owner", "Parish", "Municipality", "Island");
        assertEquals("1", propriedade.getParId(), "The parId should match the value provided in the constructor.");
    }

    @Test
    void getGeometry() {
        // Test that the geometry is returned correctly
        Geometry geometry = new GeometryFactory().createPoint();
        Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", geometry, "Owner", "Parish", "Municipality", "Island");
        assertEquals(geometry, propriedade.getGeometry(), "The geometry should match the value provided in the constructor.");
    }

    @Test
    void adicionarVizinho() {
        // Test that a neighbor is added correctly
        Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", null, "Owner", "Parish", "Municipality", "Island");
        propriedade.adicionarVizinho("2");
        assertTrue(propriedade.getVizinhos().contains("2"), "The neighbor ID should be added to the set of neighbors.");
    }

    @Test
    void getVizinhos() {
        // Test that the set of neighbors is returned correctly
        Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", null, "Owner", "Parish", "Municipality", "Island");
        propriedade.adicionarVizinho("2");
        Set<String> vizinhos = propriedade.getVizinhos();
        assertNotNull(vizinhos, "The set of neighbors should not be null.");
        assertEquals(1, vizinhos.size(), "The set of neighbors should contain exactly one element.");
        assertTrue(vizinhos.contains("2"), "The set of neighbors should contain the added neighbor ID.");
    }

    @Test
    void toStringTest() {
        // Test that the toString method returns the correct string representation
        Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", null, "Owner", "Parish", "Municipality", "Island");
        propriedade.adicionarVizinho("2");
        String expected = "Parcela 1 (Municipality - Parish - Island) com vizinhos: [2]";
        assertEquals(expected, propriedade.toString(), "The string representation should match the expected format.");
    }
}