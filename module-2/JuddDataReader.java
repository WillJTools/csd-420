/*
 * Author: William Judd II
 * Date: November 1, 2026
 * Course: CSD420 Advanced Java Programming
 * Assignment: Module 2.2
 *
 * Purpose:
 * Read and display all saved integer and double arrays
 * from the binary data file.
 */

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class JuddDataReader {

    private static final String FILE_NAME = "judd_datafile.dat";
    private static final int ARRAY_SIZE = 5;

    public static void main(String[] args) {

        try {
            long fileSize = java.nio.file.Files.size(
                    java.nio.file.Path.of(FILE_NAME));

            if (fileSize % 60 != 0) {
                System.err.println(
                        "Error: Data file contains an incomplete record.");
                return;
            }
        } catch (IOException e) {
            System.err.println("Unable to inspect data file: "
                    + e.getMessage());
            return;
        }

        try (DataInputStream input = new DataInputStream(
                new FileInputStream(FILE_NAME))) {

            int recordNumber = 1;

            while (true) {

                int[] integers = new int[ARRAY_SIZE];
                double[] doubles = new double[ARRAY_SIZE];

                try {
                    // Read five integers.
                    for (int i = 0; i < ARRAY_SIZE; i++) {
                        integers[i] = input.readInt();
                    }

                    // Read five doubles.
                    for (int i = 0; i < ARRAY_SIZE; i++) {
                        doubles[i] = input.readDouble();
                    }

                } catch (EOFException e) {
                    // Normal EOF occurs between complete records.
                    break;
                }

                System.out.println("\nRecord " + recordNumber);

                System.out.println("Integers:");
                for (int number : integers) {
                    System.out.print(number + " ");
                }

                System.out.println("\nDoubles:");
                for (double number : doubles) {
                    System.out.printf("%.4f ", number);
                }

                System.out.println();

                recordNumber++;
            }

            System.out.println("\nTotal records read: "
                    + (recordNumber - 1));

        } catch (IOException e) {
            System.err.println("Error reading data file: "
                    + e.getMessage());
        }
    }
}
