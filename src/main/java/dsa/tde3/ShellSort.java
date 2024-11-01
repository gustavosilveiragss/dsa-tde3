package dsa.tde3;

public class ShellSort extends SortingAlgorithm {
    public ShellSort(boolean debug) {
        super(debug);
    }

    @Override
    public void sort(int[] array) {
        iterations = 0;
        swaps = 0;

        // Inicia distância das comparações
        // Várias sequências de gaps podem ser utilizadas, essa implementação utiliza a sequência de Knuth
        // gap = 1, 4 (3 * 1 + 1), 13 (3 * 4 + 1), 40 (3 * 13 + 1), 121 (3 * 40 + 1), ...
        int gap = 1;
        while (gap < (array.length / 3)) gap = 3 * gap + 1;

        if (DEBUG)
            printGapSequence(gap);

        // Reduz a distância entre comparações até gap = 1
        while (gap > 0) {
            if (DEBUG) {
                System.out.printf("\nComparação com Distância = %d:%n", gap);
                printArrayWithGaps(array, gap, 0);
            }

            // Percorre o array a partir do gap. Aqui será realizado um insertion sort para cada grupo de elementos
            for (int i = gap; i < array.length; i++) {
                iterations++;

                // Elemento a ser inserido no grupo ordenado
                int temp = array[i];

                if (DEBUG)
                    System.out.printf("  Comparando o elemento da posição %d com o da posição %d: \n", i - gap, i);

                // Move os elementos maiores que temp para frente
                int j = i;
                while (j >= gap && array[j - gap] > temp) {
                    iterations++;
                    if (DEBUG)
                        System.out.printf("  Movendo \"%d\" da posição %d para posição %d%n", array[j - gap], j - gap, j);
                    array[j] = array[j - gap];
                    swaps++;
                    j -= gap;
                }

                // Insere temp na posição correta
                if (j != i) {
                    array[j] = temp;
                    swaps++;
                }

                if (DEBUG)
                    printArrayWithGaps(array, gap, 1);
            }

            // Para calcular o próximo gap, reverte a fórmula de Knuth
            gap /= 3;

            if (DEBUG && gap > 0)
                System.out.printf("\nReduzindo distância para %d%n", gap);
        }

        if (DEBUG) {
            System.out.print("\nVetor ordenado final: ");
            printArray(array);
            System.out.println("Total de trocas: " + swaps);
            System.out.println("Total de iterações: " + iterations);
        }
    }

    // Métodos auxiliares para visualização da execução do algoritmo

    private void printGapSequence(int initialGap) {
        System.out.print("\nSequência de Distâncias:");
        for (int gap = initialGap; gap > 0; gap /= 3)
            System.out.printf("%d ", gap);
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
        System.out.print("Valor:  ");
        for (int j : array)
            System.out.printf("%-4d", j);
        System.out.println();

        System.out.print(offset);
        System.out.print("Grupos: ");
        for (int i = 0; i < array.length; i++) {
            String marker = (i % gap == 0) ? "*   " : "    ";
            System.out.print(marker);
        }
        System.out.println();
    }
}