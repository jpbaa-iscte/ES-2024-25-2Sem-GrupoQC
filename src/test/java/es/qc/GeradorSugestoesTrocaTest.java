package es.qc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GeradorSugestoesTrocaTest {

    /**
     * Monta um mapa de propriedades de teste:
     *
     * - p1 e p2 são adjacentes, donos diferentes, mas troca não gera ganho.
     * - p3 e p4 são adjacentes, donos diferentes, e você poderá ajustar
     *   a lógica futuramente para produzir ganho neste par.
     */
    private Map<String, Propriedade> criarMapaTeste() {
        Map<String, Propriedade> mapa = new HashMap<>();

        Propriedade p1 = new Propriedade("1", "001", 0, 100, "", null, "A", "F", "M", "I");
        Propriedade p2 = new Propriedade("2", "002", 0, 200, "", null, "B", "F", "M", "I");
        Propriedade p3 = new Propriedade("3", "003", 0, 50,  "", null, "C", "F", "M", "I");
        Propriedade p4 = new Propriedade("4", "004", 0, 150, "", null, "D", "F", "M", "I");

        // Define adjacências
        p1.adicionarVizinho("2");
        p2.adicionarVizinho("1");
        p3.adicionarVizinho("4");
        p4.adicionarVizinho("3");

        mapa.put(p1.getParId(), p1);
        mapa.put(p2.getParId(), p2);
        mapa.put(p3.getParId(), p3);
        mapa.put(p4.getParId(), p4);

        return mapa;
    }

    @Test
    public void testSemSugestoesQuandoNaoHaGanho() {
        Map<String, Propriedade> mapa = criarMapaTeste();
        List<SugestaoTroca> sugestoes = GeradorSugestoesTroca.sugerirTrocas(mapa);

        // Na implementação atual, nenhuma troca gera ganho > 0
        assertTrue(sugestoes.isEmpty(), "Não deve gerar sugestões quando não há ganho positivo");
    }

}
