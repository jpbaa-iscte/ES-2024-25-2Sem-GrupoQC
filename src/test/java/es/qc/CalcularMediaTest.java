package es.qc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalcularMediaTest {

    private Map<String, Propriedade> criarPropriedadesSimples() {
        Map<String, Propriedade> mapa = new HashMap<>();

        // Correto seria:
        Propriedade p1 = new Propriedade("1", "001", 100, 200, "", null, "OwnerA", "Santa Maria Maior", "Funchal", "Madeira");
        Propriedade p2 = new Propriedade("2", "002", 100, 300, "", null, "OwnerA", "Santa Maria Maior", "Funchal", "Madeira");
        Propriedade p3 = new Propriedade("3", "003", 100, 500, "", null, "OwnerB", "Santa Maria Maior", "Funchal", "Madeira");


        // Adjacências
        p1.adicionarVizinho("2");
        p2.adicionarVizinho("1");

        mapa.put("1", p1);
        mapa.put("2", p2);
        mapa.put("3", p3);

        return mapa;
    }

    @Test
    public void testAreaMediaSimples() {
        Map<String, Propriedade> propriedades = criarPropriedadesSimples();

        double media = CalcularMedia.calcularAreaMedia(propriedades, "municipio", "Funchal");

        // (200 + 300 + 500) / 3 = 1000 / 3 ≈ 333.33
        assertEquals(333.33, media, 0.01);
    }

    @Test
    public void testAreaMediaAgrupada() {
        Map<String, Propriedade> propriedades = criarPropriedadesSimples();

        double media = CalcularMedia.calcularAreaMediaAgrupada(propriedades, "municipio", "Funchal");

        // p1 e p2 são do mesmo dono e vizinhos → 200 + 300 = 500
        // p3 é separado → 500
        // média = (500 + 500) / 2 = 500
        assertEquals(500.0, media, 0.01);
    }

    @Test
    public void testAreaMediaSemResultados() {
        Map<String, Propriedade> propriedades = criarPropriedadesSimples();

        double media = CalcularMedia.calcularAreaMedia(propriedades, "municipio", "Lisboa");
        assertEquals(0.0, media);
    }

    @Test
    public void testAreaMediaAgrupadaSemResultados() {
        Map<String, Propriedade> propriedades = criarPropriedadesSimples();

        double media = CalcularMedia.calcularAreaMediaAgrupada(propriedades, "ilha", "Continente");
        assertEquals(0.0, media);
    }
}
