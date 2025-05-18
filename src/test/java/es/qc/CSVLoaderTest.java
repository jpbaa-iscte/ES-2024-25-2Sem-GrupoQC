package es.qc;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVLoaderTest {
//teste

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new CSVLoader(), "The constructor should not throw any exceptions.");
    }

    @Test
    void carregarPropriedades1() throws Exception  {
        URL resource = getClass().getClassLoader().getResource("Madeira-Moodle-1.1.csv");
        assertNotNull(resource, "Test CSV file not found in resources.");
        String validCSVPath = Paths.get(resource.toURI()).toString();

        Map<String, Propriedade> propriedades = assertDoesNotThrow(
                () -> CSVLoader.carregarPropriedades(validCSVPath),
                "Should not throw exception for a valid CSV file."
        );
        assertNotNull(propriedades, "Returned map should not be null.");
        assertFalse(propriedades.isEmpty(), "Returned map should not be empty.");
    }

    @Test
    void carregarPropriedades2() throws Exception{
        URL resource = getClass().getClassLoader().getResource("empty.csv");
        assertNotNull(resource, "Empty CSV file not found in resources.");
        String emptyCSVPath = Paths.get(resource.toURI()).toString();

        Map<String, Propriedade> propriedades = assertDoesNotThrow(
                () -> CSVLoader.carregarPropriedades(emptyCSVPath),
                "Should not throw exception for an empty CSV file."
        );
        assertNotNull(propriedades, "Returned map should not be null.");
        assertTrue(propriedades.isEmpty(), "Returned map should be empty.");
    }

    @Test
    void carregarPropriedades3() {
        String nonExistentCSVPath = "nonexistent.csv"; // This file doesn't exist

        Exception exception = assertThrows(
                Exception.class,
                () -> CSVLoader.carregarPropriedades(nonExistentCSVPath),
                "Should throw exception for a non-existent file."
        );
        assertTrue(
                exception.getMessage().contains("nonexistent.csv"),
                "Exception message should mention missing file."
        );
    }
}
