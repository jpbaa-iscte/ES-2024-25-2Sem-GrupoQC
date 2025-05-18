package es.qc;

public class SugestaoTroca {
    private final Propriedade propA;
    private final Propriedade propB;
    private final double ganhoTotal;
    private final double diferencaArea;
    private final double similaridade;
    private final double scoreFinal;


    public SugestaoTroca(Propriedade propA, Propriedade propB, double ganhoTotal, double diferencaArea, double similaridade, double scoreFinal) {
        this.propA = propA;
        this.propB = propB;
        this.ganhoTotal = ganhoTotal;
        this.diferencaArea = diferencaArea;
        this.similaridade = similaridade;
        this.scoreFinal = scoreFinal;
    }

    public Propriedade getPropA() {
        return propA;
    }

    public Propriedade getPropB() {
        return propB;
    }

    public double getGanhoTotal() {
        return ganhoTotal;
    }

    public double getDiferencaArea() {
        return diferencaArea;
    }

    public double getSimilaridade() {
        return similaridade;
    }

    public double getScoreFinal() {return scoreFinal;}

    @Override
    public String toString() {
        return String.format("Trocar %s (%s, %.2fm²) com %s (%s, %.2fm²) → Ganho: %.2f, Diferença de área: %.2f, Similaridade: %.2f, ScoreFinal: %.2f ",
                propA.getParId(), propA.getOwner(), propA.getArea(),
                propB.getParId(), propB.getOwner(), propB.getArea(),
                ganhoTotal, diferencaArea, similaridade, scoreFinal);
    }
}
