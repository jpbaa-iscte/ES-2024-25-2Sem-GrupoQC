package es.qc;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for {@link CSVLoader}.
 * <p>
 * Author: jxbarbosax
 * Date: 2023-10-10
 * </p>
 * <p>
 * Cyclomatic Complexity (CC) for each method:
 * <ul>
 *     <li>Constructor: CC = 1</li>
 *     <li>carregarPropriedades: CC = 3</li>
 * </ul>
 */
class CSVLoaderTest {

    /**
     * Test for the constructor of {@link CSVLoader}.
     */
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new CSVLoader(), "The constructor should not throw any exceptions.");
    }

    /**
     * Test for {@link CSVLoader#carregarPropriedades(String)} - Path 1.
     * Valid CSV file with data.
     */
    @Test
    void carregarPropriedades1() {
        URL resource = getClass().getClassLoader().getResource("Madeira-Moodle-1.1.csv");
        assertNotNull(resource, "Test CSV file not found in resources.");
        String validCSVPath = resource.getPath();

        Map<String, Propriedade> propriedades = assertDoesNotThrow(
                () -> CSVLoader.carregarPropriedades(validCSVPath),
                "Should not throw exception for a valid CSV file."
        );
        assertNotNull(propriedades, "Returned map should not be null.");
        assertFalse(propriedades.isEmpty(), "Returned map should not be empty.");
    }

    /**
     * Test for {@link CSVLoader#carregarPropriedades(String)} - Path 2.
     * Empty CSV file.
     */
    @Test
    void carregarPropriedades2() {
        URL resource = getClass().getClassLoader().getResource("empty.csv");
        assertNotNull(resource, "Empty CSV file not found in resources.");
        String emptyCSVPath = resource.getPath();

        Map<String, Propriedade> propriedades = assertDoesNotThrow(
                () -> CSVLoader.carregarPropriedades(emptyCSVPath),
                "Should not throw exception for an empty CSV file."
        );
        assertNotNull(propriedades, "Returned map should not be null.");
        assertTrue(propriedades.isEmpty(), "Returned map should be empty.");
    }

    /**
     * Test for {@link CSVLoader#carregarPropriedades(String)} - Path 3.
     * Non-existent CSV file.
     */
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
