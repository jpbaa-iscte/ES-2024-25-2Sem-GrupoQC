package es.qc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalcularMediaTest {

    private Map<String, Propriedade> criarPropriedadesDeTeste() {
        Map<String, Propriedade> mapa = new HashMap<>();

        Propriedade p1 = new Propriedade("1", "001", 50, 100, "", null, "João", "Funchal", "Funchal", "Madeira");
        Propriedade p2 = new Propriedade("2", "002", 75, 200, "", null, "João", "Funchal", "Funchal", "Madeira");
        Propriedade p3 = new Propriedade("3", "003", 80, 300, "", null, "Maria", "Funchal", "Funchal", "Madeira");
        Propriedade p4 = new Propriedade("4", "004", 60, 400, "", null, "Carlos", "Calheta", "Calheta", "Madeira");

        // Adjacência entre p1 e p2 (mesmo dono)
        p1.adicionarVizinho("2");
        p2.adicionarVizinho("1");

        // Nenhuma ligação para p3 e p4

        mapa.put(p1.getParId(), p1);
        mapa.put(p2.getParId(), p2);
        mapa.put(p3.getParId(), p3);
        mapa.put(p4.getParId(), p4);

        return mapa;
    }

    @Test
    public void testCalcularAreaMediaPorMunicipio() {
        Map<String, Propriedade> props = criarPropriedadesDeTeste();
        double resultado = CalcularMedia.calcularAreaMediaPorLocalizacao(props, "Funchal", "municipio");

        // Funchal tem: 100 + 200 + 300 = 600 / 3 = 200
        assertEquals(200.0, resultado, 0.01);
    }

    @Test
    public void testCalcularAreaMediaPorFreguesia() {
        Map<String, Propriedade> props = criarPropriedadesDeTeste();
        double resultado = CalcularMedia.calcularAreaMediaPorLocalizacao(props, "Calheta", "freguesia");

        // Calheta tem apenas p4 com 400
        assertEquals(400.0, resultado, 0.01);
    }

    @Test
    public void testCalcularAreaMediaAgrupadaPorMunicipio() {
        Map<String, Propriedade> props = criarPropriedadesDeTeste();
        double resultado = CalcularMedia.calcularAreaMediaAgrupada(props, "municipio", "Funchal");

        // João: p1 (100) + p2 (200) são vizinhas → 300
        // Maria: p3 (300) → isolada
        // média = (300 + 300) / 2 = 300
        assertEquals(300.0, resultado, 0.01);
    }

    @Test
    public void testCalcularAreaMediaAgrupadaSemResultados() {
        Map<String, Propriedade> props = criarPropriedadesDeTeste();
        double resultado = CalcularMedia.calcularAreaMediaAgrupada(props, "municipio", "Lisboa");

        assertEquals(0.0, resultado, 0.01);
    }
}
