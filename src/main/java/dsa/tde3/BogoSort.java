package dsa.tde3;

import java.util.Random;

public class BogoSort extends SortingAlgorithm {
    private final boolean DEBUG;

    public BogoSort(boolean debug) {
        DEBUG = debug;
    }

    @Override
    public void sort(int[] arr) {
        int attempts = 0;
        Random random = new Random();

        // Aleatoriamente troca a ordem de elementos até ordenar o vetor
        while (!isSorted(arr)) {
            // Troca a posição de elementos aleatoriamente
            for (int i = arr.length - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }

            if (DEBUG) {
                System.out.printf("Attempt %d: ", ++attempts);
                printArray(arr);
            }
        }

        if (DEBUG) {
            System.out.printf("Final sorted array after %d attempts: ", attempts);
            printArray(arr);
        }
    }
}
