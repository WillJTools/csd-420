
import java.util.Arrays;
import java.util.Comparator;

/**
 * CSD420 Module 6.2 - Bubble Sort Test Program
 *
 * Author: William Judd II
 * Date: November 22, 2026
 *
 * Tests both generic bubble sort implementations.
 */
public class BubbleSortTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        // Comparable: sort integers in ascending order.
        Integer[] numbers = {9, 3, 7, 1, 5};
        BubbleSort.bubbleSort(numbers);

        check("Comparable - integers",
                numbers, new Integer[]{1, 3, 5, 7, 9});

        // Comparable: sort strings alphabetically.
        String[] names = {"ZAP", "Burp", "Nmap", "Nuclei"};
        BubbleSort.bubbleSort(names);

        check("Comparable - strings",
                names,
                new String[]{"Burp", "Nmap", "Nuclei", "ZAP"});

        // Comparator: sort severity scores from highest to lowest.
        Integer[] severityScores = {4, 9, 2, 10, 7};
        BubbleSort.bubbleSort(
                severityScores, Comparator.reverseOrder());

        check("Comparator - severity descending",
                severityScores,
                new Integer[]{10, 9, 7, 4, 2});

        // Comparator: sort findings by their name length.
        String[] findings = {
                "SQLi", "Broken Access Control", "XSS", "SSRF"
        };

        BubbleSort.bubbleSort(
                findings, Comparator.comparingInt(String::length));

        check("Comparator - finding name length",
                findings,
                new String[]{
                        "XSS", "SQLi", "SSRF", "Broken Access Control"
                });

        // Additional edge cases.
        Integer[] empty = {};
        BubbleSort.bubbleSort(empty);
        check("Empty array", empty, new Integer[]{});

        Integer[] duplicates = {3, 1, 3, 2, 1};
        BubbleSort.bubbleSort(duplicates);
        check("Duplicate values",
                duplicates, new Integer[]{1, 1, 2, 3, 3});

        Integer[] sorted = {1, 2, 3, 4};
        BubbleSort.bubbleSort(sorted);
        check("Already sorted",
                sorted, new Integer[]{1, 2, 3, 4});

        try {
            BubbleSort.bubbleSort((Integer[]) null);
            record("Null array validation", false);
        } catch (NullPointerException exception) {
            System.out.println("Expected exception: "
                    + exception.getMessage());
            record("Null array validation", true);
        }

        try {
            BubbleSort.bubbleSort(
                    new Integer[]{2, 1}, null);
            record("Null comparator validation", false);
        } catch (NullPointerException exception) {
            System.out.println("Expected exception: "
                    + exception.getMessage());
            record("Null comparator validation", true);
        }

        System.out.println("\nTests passed: " + passed);
        System.out.println("Tests failed: " + failed);

        if (failed > 0) {
            throw new AssertionError(
                    failed + " test(s) failed.");
        }
    }

    private static <T> void check(
            String name, T[] actual, T[] expected) {

        System.out.println(name + ": "
                + Arrays.toString(actual));

        record(name, Arrays.equals(actual, expected));
    }

    private static void record(String name, boolean success) {
        if (success) {
            passed++;
            System.out.println("PASS: " + name + "\n");
        } else {
            failed++;
            System.err.println("FAIL: " + name + "\n");
        }
    }
}

