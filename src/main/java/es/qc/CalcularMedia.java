package es.qc;
import java.util.Map;

public class CalcularMedia {
    /**
     * Calcula a área média das propriedades com base no nível geográfico e nome.
     *
     * @param propriedades Mapa de propriedades
     * @param tipoArea     Tipo de região: "freguesia", "municipio" ou "ilha"
     * @param nome         Nome da região
     * @return Área média (0.0 se nenhuma propriedade for encontrada)
     */
    public static double calcularAreaMedia(Map<String, Propriedade> propriedades, String tipoArea, String nome) {
        double somaAreas = 0.0;
        int contador = 0;

        for (Propriedade prop : propriedades.values()) {
            boolean corresponde = false;
            String tipo = tipoArea.toLowerCase();

            if (tipo.equals("freguesia")) {
                corresponde = prop.getFreguesia().equalsIgnoreCase(nome);
            } else if (tipo.equals("municipio")) {
                corresponde = prop.getMunicipio().equalsIgnoreCase(nome);
            } else if (tipo.equals("ilha")) {
                corresponde = prop.getIlha().equalsIgnoreCase(nome);
            }

            if (corresponde) {
                somaAreas += prop.getArea();
                contador++;
            }
        }

        return (contador > 0) ? somaAreas / contador : 0.0;
    }
}
