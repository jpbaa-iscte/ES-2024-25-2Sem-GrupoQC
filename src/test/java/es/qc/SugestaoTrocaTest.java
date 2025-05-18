package es.qc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SugestaoTrocaTest {

    @Test
    public void testGettersAndToString() {
        Propriedade p1 = new Propriedade("1", "001", 0, 150.0,
                "", null, "Alice", "FreguesiaX", "MunicipioX", "IlhaX");
        Propriedade p2 = new Propriedade("2", "002", 0, 100.0,
                "", null, "Bob", "FreguesiaY", "MunicipioY", "IlhaY");

        double ganho = 25.0;
        double diffArea = Math.abs(p1.getArea() - p2.getArea()); // 50.0
        double similaridade = 0.8;
        double scoreFinal = 0.7 * ganho + 0.3 * similaridade;

        SugestaoTroca sugestao = new SugestaoTroca(p1, p2, ganho, diffArea);


        // getters
        assertSame(p1, sugestao.getPropA());
        assertSame(p2, sugestao.getPropB());
        assertEquals(ganho, sugestao.getGanhoTotal(), 1e-6);
        assertEquals(diffArea, sugestao.getDiferencaArea(), 1e-6);

        String s = sugestao.toString();

        assertAll("toString deve conter:",
                () -> assertTrue(s.startsWith("Trocar 1"),           "deve começar por 'Trocar 1'"),
                () -> assertTrue(s.contains("(Alice"),               "deve conter '(Alice'"),
                () -> assertTrue(s.contains("(Bob"),                 "deve conter '(Bob'"),
                () -> assertTrue(s.contains("150") && s.contains("m²"), "deve conter '150' seguido de 'm²'"),
                () -> assertTrue(s.contains("100") && s.contains("m²"), "deve conter '100' seguido de 'm²'"),
                () -> assertTrue(s.contains("Ganho:"),               "deve conter 'Ganho:'"),
                () -> assertTrue(s.contains("Diferença de área"),     "deve conter 'Diferença de área'")
        );
    }
}
