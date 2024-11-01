package dsa.tde3;

import java.util.Random;

public class BogoSort extends SortingAlgorithm {
    public BogoSort(boolean debug) {
        super(debug);
    }

    @Override
    public void sort(int[] arr) {
        iterations = 0;
        swaps = 0;

        int attempts = 0;
        Random random = new Random();

        // Aleatoriamente troca a ordem de elementos até ordenar o vetor
        while (!isSorted(arr)) {
            iterations++;

            // Troca a posição de elementos aleatoriamente
            for (int i = arr.length - 1; i > 0; i--) {
                iterations++;
                int j = random.nextInt(i + 1);
                if (i != j) {
                    swap(arr, i, j);
                }
            }

            if (DEBUG) {
                System.out.printf("Tentativa %d: ", ++attempts);
                printArray(arr);
            }
        }

        if (DEBUG) {
            System.out.printf("Vetor ordenado final, depois de %d tentativas: ", attempts);
            printArray(arr);
            System.out.println("Total de trocas: " + swaps);
            System.out.println("Total de iterações: " + iterations);
        }
    }
}
