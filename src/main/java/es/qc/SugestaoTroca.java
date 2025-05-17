package es.qc;

public class SugestaoTroca {
    private final Propriedade propA;
    private final Propriedade propB;
    private final double ganhoTotal;
    private final double diferencaArea;

    public SugestaoTroca(Propriedade propA, Propriedade propB, double ganhoTotal, double diferencaArea) {
        this.propA = propA;
        this.propB = propB;
        this.ganhoTotal = ganhoTotal;
        this.diferencaArea = diferencaArea;
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

    @Override
    public String toString() {
        return String.format("Trocar %s (%s, %.2fm²) com %s (%s, %.2fm²) → Ganho: %.2f, Diferença de área: %.2f",
                propA.getParId(), propA.getOwner(), propA.getArea(),
                propB.getParId(), propB.getOwner(), propB.getArea(),
                ganhoTotal, diferencaArea);
    }
}
