/*
 * Author: William Judd II
 * Date: November 1, 2026
 * Course: CSD420 Advanced Java Programming
 * Assignment: Module 2.2
 *
 * Purpose:
 * Generate five random integers and five random doubles.
 * Save both arrays to a binary data file, creating the file
 * if necessary or appending to it if it already exists.
 */

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class JuddDataWriter {

    private static final String FILE_NAME = "judd_datafile.dat";
    private static final int ARRAY_SIZE = 5;

    public static void main(String[] args) {

        int[] integers = new int[ARRAY_SIZE];
        double[] doubles = new double[ARRAY_SIZE];

        Random random = new Random();

        // Generate the two arrays.
        for (int i = 0; i < ARRAY_SIZE; i++) {
            integers[i] = random.nextInt(100) + 1;
            doubles[i] = random.nextDouble() * 100;
        }

        // Append a complete set of five integers and five doubles.
        try (DataOutputStream output = new DataOutputStream(
                new FileOutputStream(FILE_NAME, true))) {

            for (int number : integers) {
                output.writeInt(number);
            }

            for (double number : doubles) {
                output.writeDouble(number);
            }

            System.out.println("Data successfully saved to "
                    + FILE_NAME);

            System.out.println("Integers generated:");
            for (int number : integers) {
                System.out.print(number + " ");
            }

            System.out.println("\nDoubles generated:");
            for (double number : doubles) {
                System.out.printf("%.4f ", number);
            }

            System.out.println();

        } catch (IOException e) {
            System.err.println("Error writing to data file: "
                    + e.getMessage());
        }
    }
}
