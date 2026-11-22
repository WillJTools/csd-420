
import java.util.Comparator;
import java.util.Objects;

/**
 * CSD420 Module 6.2 - Generic Bubble Sort
 *
 * Author: William Judd II
 * Date: November 22, 2026
 *
 * Implements bubble sort using Comparable and Comparator.
 */
public class BubbleSort {

    /**
     * Sorts an array using the natural ordering of its elements.
     *
     * @param <T> the type of elements being sorted
     * @param items the array to sort
     * @throws NullPointerException if the array is null
     */
    public static <T extends Comparable<? super T>>
            void bubbleSort(T[] items) {

        Objects.requireNonNull(items, "The array cannot be null.");

        for (int pass = 0; pass < items.length - 1; pass++) {
            boolean swapped = false;

            for (int i = 0; i < items.length - 1 - pass; i++) {
                if (items[i].compareTo(items[i + 1]) > 0) {
                    swap(items, i);
                    swapped = true;
                }
            }

            // Stop early if the array is already sorted.
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Sorts an array using a supplied Comparator.
     *
     * @param <T> the type of elements being sorted
     * @param items the array to sort
     * @param comparator the comparison rule
     * @throws NullPointerException if an argument is null
     */
    public static <T> void bubbleSort(
            T[] items, Comparator<? super T> comparator) {

        Objects.requireNonNull(items, "The array cannot be null.");
        Objects.requireNonNull(
                comparator, "The comparator cannot be null.");

        for (int pass = 0; pass < items.length - 1; pass++) {
            boolean swapped = false;

            for (int i = 0; i < items.length - 1 - pass; i++) {
                if (comparator.compare(items[i], items[i + 1]) > 0) {
                    swap(items, i);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Exchanges two adjacent elements.
     * Shared by both sorting methods to avoid duplicate code.
     */
    private static <T> void swap(T[] items, int index) {
        T temporary = items[index];
        items[index] = items[index + 1];
        items[index + 1] = temporary;
    }
}

