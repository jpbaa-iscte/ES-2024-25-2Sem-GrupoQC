package es.qc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GrafoProprietariosTest {

    private Geometry dummyGeometry;

    @BeforeEach
    void setup() {
        GeometryFactory factory = new GeometryFactory();
        dummyGeometry = factory.createPoint(new Coordinate(0, 0));
    }

    private Propriedade criarPropriedade(String id, String owner) {
        return new Propriedade(id, id, 10.0, 100.0, "", dummyGeometry, owner, "Freguesia", "Municipio", "Ilha");
    }

    @Test
    void testGrafoComVizinhosDiferentesProprietarios() {
        GrafoPropriedades grafoPropriedades = new GrafoPropriedades();

        Propriedade p1 = criarPropriedade("1", "Alice");
        Propriedade p2 = criarPropriedade("2", "Bob");
        Propriedade p3 = criarPropriedade("3", "Alice");

        // Definir vizinhança
        p1.adicionarVizinho("2"); // Alice -> Bob
        p2.adicionarVizinho("1"); // Bob -> Alice
        p2.adicionarVizinho("3"); // Bob -> Alice (de novo)
        p3.adicionarVizinho("2"); // Alice -> Bob

        // Adicionar ao grafo
        grafoPropriedades.adicionarPropriedade(p1);
        grafoPropriedades.adicionarPropriedade(p2);
        grafoPropriedades.adicionarPropriedade(p3);

        GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);
        Map<String, Set<String>> grafo = grafoProprietarios.getGrafo();

        // Verificações
        assertEquals(2, grafo.size()); // Apenas dois proprietários únicos
        assertTrue(grafo.containsKey("Alice"));
        assertTrue(grafo.containsKey("Bob"));

        assertEquals(Set.of("Bob"), grafo.get("Alice"));
        assertEquals(Set.of("Alice"), grafo.get("Bob"));
    }

    @Test
    void testGrafoComVizinhosMesmoProprietario() {
        GrafoPropriedades grafoPropriedades = new GrafoPropriedades();

        Propriedade p1 = criarPropriedade("1", "Carlos");
        Propriedade p2 = criarPropriedade("2", "Carlos");

        p1.adicionarVizinho("2");
        p2.adicionarVizinho("1");

        grafoPropriedades.adicionarPropriedade(p1);
        grafoPropriedades.adicionarPropriedade(p2);

        GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);
        Map<String, Set<String>> grafo = grafoProprietarios.getGrafo();

        assertEquals(1, grafo.size());
        assertTrue(grafo.containsKey("Carlos"));
        assertTrue(grafo.get("Carlos").isEmpty()); // Sem vizinhos diferentes
    }

    @Test
    void testGrafoVazio() {
        GrafoPropriedades grafoPropriedades = new GrafoPropriedades();

        GrafoProprietarios grafoProprietarios = new GrafoProprietarios(grafoPropriedades);
        Map<String, Set<String>> grafo = grafoProprietarios.getGrafo();

        assertTrue(grafo.isEmpty());
    }
}
