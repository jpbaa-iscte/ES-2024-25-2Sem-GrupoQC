package es.qc;

import org.junit.jupiter.api.Test;

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
        // Test that the CSVLoader object is created successfully
        assertDoesNotThrow(() -> new CSVLoader(), "The constructor should not throw any exceptions.");
    }

    /**
     * Test for {@link CSVLoader#carregarPropriedades(String)} - Path 1.
     * This path tests the case where the CSV file is valid and contains data.
     */
    @Test
    void carregarPropriedades1() {
        String validCSVPath = "valid.csv";

        // Test that the method returns a non-null map
        Map<String, Propriedade> propriedades = assertDoesNotThrow(() -> CSVLoader.carregarPropriedades(validCSVPath),
                "The method should not throw an exception for a valid CSV file.");
        assertNotNull(propriedades, "The returned map should not be null for a valid CSV file.");
        assertFalse(propriedades.isEmpty(), "The returned map should not be empty for a valid CSV file.");
    }

    /**
     * Test for {@link CSVLoader#carregarPropriedades(String)} - Path 2.
     * This path tests the case where the CSV file is empty.
     */
    @Test
    void carregarPropriedades2() {
        String emptyCSVPath = "empty.csv";

        // Test that the method returns an empty map
        Map<String, Propriedade> propriedades = assertDoesNotThrow(() -> CSVLoader.carregarPropriedades(emptyCSVPath),
                "The method should not throw an exception for an empty CSV file.");
        assertNotNull(propriedades, "The returned map should not be null for an empty CSV file.");
        assertTrue(propriedades.isEmpty(), "The returned map should be empty for an empty CSV file.");
    }

    /**
     * Test for {@link CSVLoader#carregarPropriedades(String)} - Path 3.
     * This path tests the case where the CSV file does not exist.
     */
    @Test
    void carregarPropriedades3() {
        String nonExistentCSVPath = "nonexistent.csv";

        // Test that the method throws an exception for a non-existent file
        Exception exception = assertThrows(Exception.class,
                () -> CSVLoader.carregarPropriedades(nonExistentCSVPath),
                "The method should throw an exception for a non-existent CSV file.");
        assertEquals("File not found: nonexistent.csv", exception.getMessage(),
                "The exception message should indicate that the file was not found.");
    }
}