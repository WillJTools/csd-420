/**
 * CSD420 Module 3.2: Remove duplicate values from an ArrayList.
 *
 * Author: William Judd
 * Date: November 8, 2026
 * Course: CSD420 Advanced Java Programming
 * Assignment: Module 3.2 Programming Assignment
 *
 * This console program creates 50 random integers from 1 through 20,
 * removes repeated values using a generic static method, and checks
 * that the returned list contains no duplicates. The original list
 * is preserved so callers can continue using their unmodified data.
 *
 * Attribution: Original assignment implementation written for CSD420.
 * Java standard library classes are documented by Oracle.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class RemoveDuplicatesTest {

    /** Number of random values required by the assignment. */
    private static final int VALUE_COUNT = 50;

    /** Smallest and largest permitted random integers, inclusive. */
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 20;

    /**
     * Creates a new ArrayList containing only the first occurrence
     * of each element in the supplied list.
     *
     * LinkedHashSet uses equality comparisons to remove duplicates
     * and preserves insertion order. Converting it to an ArrayList
     * satisfies the assignment's required return type. This method
     * does not add, remove, or reorder elements in the input list.
     *
     * @param <E> the element type stored in the input ArrayList
     * @param list the source ArrayList; must not be null
     * @return a new ArrayList with duplicates removed in first-seen order
     * @throws IllegalArgumentException if list is null
     */
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {

        if (list == null) {
            throw new IllegalArgumentException("Input list cannot be null.");
        }

        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    /**
     * Generates the required random list, displays both lists, and
     * verifies the behavior using clear pass or fail checks.
     *
     * @param args command line arguments; none are required
     */
    public static void main(String[] args) {

        try {
            Random random = new Random();
            ArrayList<Integer> originalValues = new ArrayList<>();

            // nextInt(bound) starts at zero. Adding MIN_VALUE shifts
            // the result into the inclusive range of 1 through 20.
            for (int index = 0; index < VALUE_COUNT; index++) {

                int value = random.nextInt(MAX_VALUE - MIN_VALUE + 1)
                        + MIN_VALUE;

                originalValues.add(value);
            }

            // Keep a snapshot to prove the method leaves its input intact.
            ArrayList<Integer> originalSnapshot =
                    new ArrayList<>(originalValues);

            ArrayList<Integer> uniqueValues =
                    removeDuplicates(originalValues);

            System.out.println("Original 50 values: " + originalValues);
            System.out.println("Original count: " + originalValues.size());
            System.out.println("Values without duplicates: " + uniqueValues);
            System.out.println("Unique count: " + uniqueValues.size());

            // The set comparison confirms that all distinct original
            // values remain present in the returned ArrayList.
            Set<Integer> originalSet = new HashSet<>(originalValues);
            Set<Integer> uniqueSet = new HashSet<>(uniqueValues);

            boolean passed = originalValues.size() == VALUE_COUNT
                    && originalValues.equals(originalSnapshot)
                    && uniqueValues != originalValues
                    && uniqueValues.size() == uniqueSet.size()
                    && originalSet.equals(uniqueSet)
                    && originalValues.stream().allMatch(value ->
                            value >= MIN_VALUE && value <= MAX_VALUE);

            // A deterministic generic test checks that the method also
            // handles Strings and preserves their first occurrence order.
            ArrayList<String> words = new ArrayList<>();

            words.add("API");
            words.add("Network");
            words.add("API");
            words.add("Web");

            ArrayList<String> uniqueWords = removeDuplicates(words);

            boolean genericTestPassed = uniqueWords.equals(
                    new ArrayList<>(java.util.List.of(
                            "API", "Network", "Web")));

            System.out.println("Integer validation: "
                    + (passed ? "PASS" : "FAIL"));

            System.out.println("Generic String validation: "
                    + (genericTestPassed ? "PASS" : "FAIL"));

            if (!passed || !genericTestPassed) {
                throw new IllegalStateException(
                        "Duplicate removal validation failed.");
            }

        } catch (IllegalArgumentException | IllegalStateException error) {

            // Report meaningful failures to the console for debugging.
            System.err.println("Validation error: " + error.getMessage());
            System.exit(1);
        }
    }
}
