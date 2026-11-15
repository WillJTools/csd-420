import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Student: William Judd II
 * Course: CSD420 - Advanced Java Programming
 * Assignment: Module 5.2
 * Date: November 15, 2026
 *
 * Description:
 * Reads words from a text file, removes duplicates, and displays
 * the remaining words in ascending and descending alphabetical order.
 *
 * The program uses a TreeSet because it automatically sorts the words
 * and prevents duplicate entries from being stored.
 */
public class WordCollection {

    // The assignment requires the input file to be referenced directly
    // in the program rather than provided as a command-line argument.
    private static final Path WORD_FILE =
            Path.of("collection_of_words.txt");

    /**
     * Reads words from the specified text file and returns a sorted
     * collection containing only unique words.
     *
     * Each word is converted to lowercase so that words such as
     * "Java" and "java" are treated as the same word.
     *
     * Punctuation at the beginning or end of a word is removed.
     *
     * @param file the path to the text file being processed
     * @return a sorted collection containing unique words
     * @throws IOException if the file cannot be opened or read
     */
    public static NavigableSet<String> readUniqueWords(Path file)
            throws IOException {

        // TreeSet automatically removes duplicate entries and keeps
        // the remaining words in ascending alphabetical order.
        NavigableSet<String> words =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        // Try-with-resources closes the Scanner automatically after
        // reading the file, even if an exception occurs.
        try (Scanner scanner =
                     new Scanner(Files.newBufferedReader(file))) {

            // Continue reading until there are no more words.
            while (scanner.hasNext()) {

                // Remove punctuation from the beginning and end
                // of each word before storing it.
                String word = scanner.next()
                        .replaceAll("^[^\\p{L}]+|[^\\p{L}]+$", "");

                // Ignore empty values and normalize capitalization
                // to prevent duplicates caused by uppercase letters.
                if (!word.isEmpty()) {
                    words.add(word.toLowerCase(Locale.ROOT));
                }
            }
        }

        // Return the unique words in alphabetical order.
        return words;
    }

    /**
     * Runs the application and displays the sorted word collections.
     *
     * @param args command-line arguments; not used by this program
     */
    public static void main(String[] args) {

        try {
            // Read the file and store the unique words.
            NavigableSet<String> words =
                    readUniqueWords(WORD_FILE);

            // TreeSet already maintains ascending alphabetical order.
            System.out.println("Ascending: " + words);

            // descendingSet() reverses the existing collection
            // without requiring another sorting algorithm.
            System.out.println(
                    "Descending: " + words.descendingSet());

            // Display the number of unique words found.
            System.out.println(
                    "Unique word count: " + words.size());

        } catch (IOException e) {

            // Display an understandable error message if the
            // input file is missing or cannot be accessed.
            System.err.println(
                    "Unable to read " + WORD_FILE + ": "
                            + e.getMessage());

            System.exit(1);
        }
    }
}
