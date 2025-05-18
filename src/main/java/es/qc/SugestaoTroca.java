package es.qc;

/**
 * Representa uma sugestão de troca entre duas propriedades, incluindo
 * os ganhos totais e a diferença de área resultante da troca.
 */
public class SugestaoTroca {

    private final Propriedade propA;
    private final Propriedade propB;
    private final double ganhoTotal;
    private final double diferencaArea;

    /**
     * Cria uma nova sugestão de troca entre duas propriedades.
     *
     * @param propA A primeira propriedade envolvida na troca.
     * @param propB A segunda propriedade envolvida na troca.
     * @param ganhoTotal O ganho total estimado com a troca.
     * @param diferencaArea A diferença de área entre as duas propriedades.
     */
    public SugestaoTroca(Propriedade propA, Propriedade propB, double ganhoTotal, double diferencaArea) {
        this.propA = propA;
        this.propB = propB;
        this.ganhoTotal = ganhoTotal;
        this.diferencaArea = diferencaArea;
    }

    /**
     * Retorna a primeira propriedade envolvida na troca.
     *
     * @return A propriedade A.
     */
    public Propriedade getPropA() {
        return propA;
    }

    /**
     * Retorna a segunda propriedade envolvida na troca.
     *
     * @return A propriedade B.
     */
    public Propriedade getPropB() {
        return propB;
    }

    /**
     * Retorna o ganho total estimado com a troca.
     *
     * @return O ganho total.
     */
    public double getGanhoTotal() {
        return ganhoTotal;
    }

    /**
     * Retorna a diferença de área entre as duas propriedades.
     *
     * @return A diferença de área.
     */
    public double getDiferencaArea() {
        return diferencaArea;
    }

    /**
     * Retorna uma representação textual da sugestão de troca.
     *
     * @return Uma string que descreve a troca sugerida.
     */
    @Override
    public String toString() {
        return String.format("Trocar %s (%s, %.2fm²) com %s (%s, %.2fm²) → Ganho: %.2f, Diferença de área: %.2f",
                propA.getParId(), propA.getOwner(), propA.getArea(),
                propB.getParId(), propB.getOwner(), propB.getArea(),
                ganhoTotal, diferencaArea);
    }
}