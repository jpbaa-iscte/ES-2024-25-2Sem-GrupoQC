package es.qc;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import static org.junit.jupiter.api.Assertions.*;

class GrafoPropriedadesTest {

    @Test
    void constructor() {
        // Test that the GrafoPropriedades object is created successfully
        assertDoesNotThrow(() -> new GrafoPropriedades(), "The constructor should not throw any exceptions.");
    }

    @Test
    void adicionarPropriedade() {
        // Test that a property is added correctly
        GrafoPropriedades grafo = new GrafoPropriedades();
        Propriedade propriedade = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", null, "Owner", "Parish", "Municipality", "Island");
        grafo.adicionarPropriedade(propriedade);
        assertTrue(grafo.getPropriedades().containsKey("1"), "The property should be added to the graph.");
    }

    @Test
    void construirGrafoAutomaticamente() throws ParseException {
        WKTReader reader = new WKTReader();

        Geometry geom1 = reader.read("POLYGON((0 0, 2 0, 2 2, 0 2, 0 0))");
        Geometry geom2 = reader.read("POLYGON((1 1, 3 1, 3 3, 1 3, 1 1))");  // Intersecta com geom1

        GrafoPropriedades grafo = new GrafoPropriedades();

        Propriedade propriedade1 = new Propriedade("1", "001", 100.0, 200.0, geom1.toText(), geom1, "Owner", "Parish", "Municipality", "Island");
        Propriedade propriedade2 = new Propriedade("2", "002", 150.0, 250.0, geom2.toText(), geom2, "Owner2", "Parish2", "Municipality2", "Island2");

        grafo.adicionarPropriedade(propriedade1);
        grafo.adicionarPropriedade(propriedade2);

        grafo.construirGrafoAutomaticamente();

        assertTrue(propriedade1.getVizinhos().contains("2"), "Property 1 should have Property 2 as a neighbor.");
        assertTrue(propriedade2.getVizinhos().contains("1"), "Property 2 should have Property 1 as a neighbor.");
    }

    @Test
    void mostrarGrafo() {
        // Test that the graph is displayed without errors
        GrafoPropriedades grafo = new GrafoPropriedades();
        Propriedade propriedade1 = new Propriedade("1", "001", 100.0, 200.0, "POINT(0 0)", null, "Owner", "Parish", "Municipality", "Island");
        Propriedade propriedade2 = new Propriedade("2", "002", 150.0, 250.0, "POINT(1 1)", null, "Owner2", "Parish2", "Municipality2", "Island2");
        grafo.adicionarPropriedade(propriedade1);
        grafo.adicionarPropriedade(propriedade2);

        assertDoesNotThrow(grafo::mostrarGrafo, "The graph should be displayed without throwing exceptions.");
    }
}