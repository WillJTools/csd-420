import java.util.Iterator;
import java.util.LinkedList;

/**
 * Compares two ways of traversing a LinkedList containing 50,000 and
 * 500,000 sequential integers. The benchmark measures traversal only;
 * list creation and result verification are outside the timed sections.
 *
 * Author: William Judd II
 * Course: CSD420 Advanced Java Programming
 * Assignment: Module 4.2 Programming Assignment
 * Date: November 8, 2026
 *
 * Attribution: Original implementation prepared for this assignment.
 * Java API behavior is documented in the Oracle LinkedList API reference.
 */
public class LinkedListTraversalBenchmark {

    private static final int SMALL_SIZE = 50_000;
    private static final int LARGE_SIZE = 500_000;

    /**
     * Builds a list of predictable integers from 0 through size - 1.
     * Predictable values make it possible to independently verify the sum.
     *
     * @param size number of integers to store; must be nonnegative
     * @return a new LinkedList containing the requested integers
     * @throws IllegalArgumentException if size is negative
     */
    public static LinkedList<Integer> createList(int size) {

        if (size < 0) {
            throw new IllegalArgumentException("List size cannot be negative.");
        }

        LinkedList<Integer> values = new LinkedList<>();

        for (int index = 0; index < size; index++) {
            values.add(index);
        }

        return values;
    }

    /**
     * Traverses the list sequentially through its iterator.
     * Each call to next() advances to the following node rather
     * than searching for an element by index.
     *
     * @param values the LinkedList to traverse
     * @return the sum of every element, stored in a long to avoid overflow
     * @throws IllegalArgumentException if the list is null
     */
    public static long sumWithIterator(LinkedList<Integer> values) {

        requireList(values);

        long sum = 0L;
        Iterator<Integer> iterator = values.iterator();

        while (iterator.hasNext()) {
            sum += iterator.next();
        }

        return sum;
    }

    /**
     * Traverses the list using get(index).
     *
     * A LinkedList does not provide constant-time indexed access.
     * Each call locates a node by walking from whichever end is
     * closer to the requested position.
     *
     * @param values the LinkedList to traverse
     * @return the sum of every element, stored in a long to avoid overflow
     * @throws IllegalArgumentException if the list is null
     */
    public static long sumWithGetIndex(LinkedList<Integer> values) {

        requireList(values);

        long sum = 0L;

        for (int index = 0; index < values.size(); index++) {
            sum += values.get(index);
        }

        return sum;
    }

    /**
     * Validates inputs so errors are reported clearly at the call site.
     */
    private static void requireList(LinkedList<Integer> values) {

        if (values == null) {
            throw new IllegalArgumentException("The LinkedList cannot be null.");
        }
    }

    /**
     * Runs lightweight assertions independently of benchmark timing.
     *
     * Verifies list construction, empty lists, valid traversal sums,
     * and the expected exceptions for invalid input.
     */
    public static void runTests() {

        LinkedList<Integer> empty = createList(0);

        assertEquals("empty iterator", 0L, sumWithIterator(empty));
        assertEquals("empty get(index)", 0L, sumWithGetIndex(empty));

        LinkedList<Integer> sample = createList(5);

        assertEquals("sample size", 5L, sample.size());
        assertEquals("sample iterator", 10L, sumWithIterator(sample));
        assertEquals("sample get(index)", 10L, sumWithGetIndex(sample));

        try {
            createList(-1);
            throw new AssertionError("Negative list size should be rejected.");

        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: negative list size rejected");
        }

        try {
            sumWithIterator(null);
            throw new AssertionError("Null list should be rejected.");

        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: null list rejected");
        }

        System.out.println("PASS: all correctness tests completed");
    }

    /**
     * Reports a failed assertion with the test name and actual result.
     *
     * @param test name of the test being checked
     * @param expected expected numeric result
     * @param actual actual numeric result
     */
    private static void assertEquals(
            String test, long expected, long actual) {

        if (expected != actual) {
            throw new AssertionError(
                    test + ": expected " + expected
                    + " but received " + actual);
        }

        System.out.println("PASS: " + test);
    }

    /**
     * Measures one complete traversal for each approach on the same list.
     *
     * Uses nanosecond timestamps and checks both results after timing.
     * List creation is excluded from the measured execution time.
     *
     * @param size number of integers used in this benchmark run
     */
    /*
     * MODULE 4.2 PERFORMANCE ANALYSIS
     *
     * Test environment:
     * Both traversal methods were executed against the same LinkedList
     * containing sequential integers. System.nanoTime() measured the
     * traversal operations without including list construction.
     *
     * RESULTS FOR 50,000 INTEGERS
     *
     * Iterator traversal:     2.252 milliseconds
     * get(index) traversal: 998.978 milliseconds
     * Performance difference: 443.5 times slower using get(index)
     *
     * RESULTS FOR 500,000 INTEGERS
     *
     * Iterator traversal:       4.604 milliseconds
     * get(index) traversal: 107,295.413 milliseconds
     * Performance difference: 23,304.4 times slower using get(index)
     *
     * ANALYSIS
     *
     * Increasing the list from 50,000 to 500,000 integers multiplied
     * the number of elements by ten.
     *
     * Iterator traversal increased from 2.252 ms to 4.604 ms.
     * This is approximately 2.04 times the original duration.
     *
     * The get(index) traversal increased from 998.978 ms to
     * 107,295.413 ms, approximately 107.4 times the original duration.
     *
     * The significant difference occurs because LinkedList is
     * implemented as a doubly linked list. An iterator advances
     * directly from one node to the next, providing O(n) traversal.
     *
     * Calling get(index) repeatedly requires the LinkedList to
     * locate each requested element by traversing nodes from the
     * beginning or end of the list. Repeating this operation for
     * every index results in O(n^2) total traversal complexity.
     *
     * This explains why the indexed approach becomes dramatically
     * slower as the number of elements increases.
     *
     * Both methods returned the expected sums for each list size,
     * confirming that the performance difference was not caused
     * by incorrect traversal results.
     *
     * These timings represent one benchmark run on this machine.
     * JVM optimization, system load, and other runtime conditions
     * may affect the exact measured durations.
     *
     * CONCLUSION
     *
     * Iterator traversal is the better approach when processing
     * every element of a LinkedList sequentially. Repeated indexed
     * access should be avoided for large LinkedList collections.
     */

    public static void benchmark(int size) {

        LinkedList<Integer> values = createList(size);

        // Expected sum of the sequence 0 through size - 1.
        // Casting to long prevents integer overflow for large lists.
        long expected = ((long) size * (size - 1)) / 2;

        // Measure sequential traversal with an Iterator.
        long iteratorStart = System.nanoTime();
        long iteratorSum = sumWithIterator(values);
        long iteratorElapsed = System.nanoTime() - iteratorStart;

        if (size == LARGE_SIZE) {
            System.out.println(
                    "Starting 500,000 get(index) operations; "
                    + "this may take several minutes or longer.");
        }

        // Measure indexed traversal using the same LinkedList.
        long getStart = System.nanoTime();
        long getSum = sumWithGetIndex(values);
        long getElapsed = System.nanoTime() - getStart;

        // Verify both methods processed the entire list correctly.
        assertEquals(size + " iterator sum", expected, iteratorSum);
        assertEquals(size + " get(index) sum", expected, getSum);

        // Convert nanoseconds to milliseconds for readable output.
        double iteratorMs = iteratorElapsed / 1_000_000.0;
        double getMs = getElapsed / 1_000_000.0;

        System.out.printf(
                "Size: %,d | Iterator: %.3f ms | "
                + "get(index): %.3f ms%n",
                size, iteratorMs, getMs);

        if (iteratorElapsed > 0) {
            System.out.printf(
                    "get(index) / iterator ratio: %.1f times%n",
                    (double) getElapsed / iteratorElapsed);
        }
    }

    /**
     * Runs correctness tests before the two required benchmark sizes.
     *
     * @param args command line arguments; none are required
     */
    public static void main(String[] args) {

        try {
            runTests();

            // Warm up the JVM with a small input before benchmarking.
            // This helps reduce some startup and JIT compilation effects.
            sumWithIterator(createList(1_000));
            sumWithGetIndex(createList(1_000));

            // First benchmark: 50,000 integers.
            benchmark(SMALL_SIZE);

            // Second benchmark: 500,000 integers.
            benchmark(LARGE_SIZE);

            System.out.println(
                    "All benchmarks completed successfully.");

        } catch (IllegalArgumentException | AssertionError error) {

            // Print a meaningful error and exit with a failure status.
            System.err.println(
                    "Validation failed: " + error.getMessage());

            System.exit(1);
        }
    }
}
