import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NavigableSet;

/**
 * Student: William Judd II
 * Course: CSD420 - Advanced Java Programming
 * Assignment: Module 5.2
 * Date: November 15, 2026
 *
 * Description:
 * Tests the WordCollection program to verify that duplicate
 * words are removed and that the remaining words are sorted
 * correctly in ascending and descending order.
 *
 * Additional tests verify empty-file handling and exceptions
 * when an input file does not exist.
 */
public class WordCollectionTest {

    // Tracks how many test conditions have been evaluated.
    private static int tests = 0;

    /**
     * Checks a test condition and reports whether it passed.
     *
     * @param condition the result of the condition being tested
     * @param description a short explanation of the test
     */
    private static void check(
            boolean condition, String description) {

        tests++;

        // Stop execution if a test fails so the problem
        // cannot be mistaken for a successful result.
        if (!condition) {
            throw new AssertionError(
                    "FAILED: " + description);
        }

        System.out.println("PASS: " + description);
    }

    /**
     * Runs the automated tests for WordCollection.
     *
     * @param args command-line arguments; not used
     * @throws IOException if temporary test files cannot be accessed
     */
    public static void main(String[] args)
            throws IOException {

        // Create temporary files so testing does not change
        // the actual assignment input file.
        Path sample =
                Files.createTempFile("words-test-", ".txt");

        Path empty =
                Files.createTempFile("words-empty-", ".txt");

        try {
            // Include repeated words, different capitalization,
            // and punctuation to test the processing logic.
            Files.writeString(
                    sample,
                    "Banana apple banana Cherry APPLE pear!\n");

            NavigableSet<String> words =
                    WordCollection.readUniqueWords(sample);

            // Test 1: Verify that duplicates and differences
            // in capitalization do not increase the word count.
            check(words.size() == 4,
                    "duplicates and capitalization removed");

            // Test 2: Verify ascending alphabetical order.
            check(
                    List.copyOf(words).equals(
                            List.of(
                                    "apple", "banana",
                                    "cherry", "pear")),
                    "ascending order");

            // Test 3: Verify descending alphabetical order.
            check(
                    List.copyOf(words.descendingSet()).equals(
                            List.of(
                                    "pear", "cherry",
                                    "banana", "apple")),
                    "descending order");

            // Test 4: Verify an empty input file produces
            // an empty collection without causing an error.
            check(
                    WordCollection.readUniqueWords(empty).isEmpty(),
                    "empty file handled");

            // Create a temporary directory and reference a file
            // that has not been created inside that directory.
            // This gives us a reliable missing-file test.
            Path missingDirectory =
                    Files.createTempDirectory("words-missing-");

            try {
                Path missing =
                        missingDirectory.resolve("missing.txt");

                // Test 5: Confirm the test file does not exist.
                check(!Files.exists(missing),
                        "missing file confirmed");

                // Test 6: Confirm the program throws IOException
                // instead of silently returning incorrect results.
                boolean threw = false;

                try {
                    WordCollection.readUniqueWords(missing);
                } catch (IOException expected) {
                    threw = true;
                }

                check(threw,
                        "missing file produces IOException");

            } finally {
                // Remove the temporary directory after testing.
                Files.deleteIfExists(missingDirectory);
            }

            System.out.println(
                    "All " + tests + " tests passed.");

        } finally {
            // Clean up temporary files whether tests
            // succeed or fail.
            Files.deleteIfExists(sample);
            Files.deleteIfExists(empty);
        }
    }
}
