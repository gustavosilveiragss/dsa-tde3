package dsa.tde3;

public class ShellSort extends SortingAlgorithm {
    private final boolean DEBUG;

    public ShellSort(boolean debug) {
        DEBUG = debug;
    }

    @Override
    public void sort(int[] array) {
        // Inicia distância das comparações
        // Várias sequências de gaps podem ser utilizadas, essa implementação utiliza a sequência de Knuth
        // gap = 1, 4 (3 * 1 + 1), 13 (3 * 4 + 1), 40 (3 * 13 + 1), 121 (3 * 40 + 1), ...
        int gap = 1;
        while (gap < (array.length / 3)) gap = 3 * gap + 1;

        if (DEBUG) {
            System.out.println("Gap sequence:");
            printGapSequence(gap);
        }

        // Reduz a distância entre comparações até gap = 1
        while (gap > 0) {
            if (DEBUG) {
                System.out.printf("\nProcessing with gap = %d:%n", gap);
                printArrayWithGaps(array, gap, 0);
            }

            // Percorre o array a partir do gap. Aqui será realizado um insertion sort para cada grupo de elementos
            for (int i = gap; i < array.length; i++) {
                // Elemento a ser inserido no grupo ordenado
                int temp = array[i];

                if (DEBUG) {
                    System.out.printf("  Inserting element %d at position %d%n\n  Comparing with elements at positions: ", temp, i);
                    for (int pos = i - gap; pos >= 0; pos -= gap)
                        System.out.printf("%d ", pos);
                    System.out.println();
                }

                // Move os elementos maiores que temp para frente
                int j = i;
                while (j >= gap && array[j - gap] > temp) {
                    if (DEBUG)
                        System.out.printf("    Moving %d from position %d to position %d%n", array[j - gap], j - gap, j);
                    array[j] = array[j - gap];
                    j -= gap;
                }

                array[j] = temp;

                if (DEBUG)
                    printArrayWithGaps(array, gap, 1);
            }

            // Para calcular o próximo gap, reverte a fórmula de Knuth
            gap /= 3;

            if (DEBUG && gap > 0)
                System.out.printf("\nReducing gap to %d%n", gap);
        }

        if (DEBUG) {
            System.out.println("\nFinal sorted array array:");
            printArray(array, array.length - 1);
        }
    }

    // Métodos auxiliares para visualização da execução do algoritmo

    private void printGapSequence(int initialGap) {
        System.out.print("Gap sequence: ");
        for (int gap = initialGap; gap > 0; gap /= 3)
            System.out.print(gap + " ");
        System.out.println();
    }

    private void printArrayWithGaps(int[] array, int gap, int depth) {
        String offset = "  ".repeat(depth);
        System.out.print(offset);

        System.out.print("Index:  ");
        for (int i = 0; i < array.length; i++)
            System.out.printf("%-4d", i);
        System.out.println();

        System.out.print(offset);
        System.out.print("Value:  ");
        for (int j : array)
            System.out.printf("%-4d", j);
        System.out.println();

        System.out.print(offset);
        System.out.print("Groups: ");
        for (int i = 0; i < array.length; i++) {
            String marker = (i % gap == 0) ? "↓   " : "    ";
            System.out.print(marker);
        }
        System.out.println();
    }

    private void printArray(int[] array, int end) {
        String offset = "  ".repeat(0);
        System.out.printf("%sArray [%d-%d]: ", offset, 0, end);
        printSubArray(array, end);
    }

    private void printSubArray(int[] array, int end) {
        System.out.print("[");
        for (int i = 0; i <= Math.min(end, 9); i++) {
            System.out.print(array[i]);
            if (i < end && i < 9) System.out.print(", ");
        }
        if (end > 9) System.out.print(", ...");
        System.out.println("]");
    }
}
