package dsa.tde3;

public class InsertionSort extends SortingAlgorithm {
    public InsertionSort(boolean debug) {
        super(debug);
    }

    @Override
    public void sort(int[] array) {
        swaps = 0;
        iterations = 0;

        // Percorre o array a partir do segundo elemento
        for (int i = 1; i < array.length; i++) {
            iterations++;

            // Guarda o elemento atual que será inserido na parte ordenada
            // Fazemos isso pois iremos deslocar elementos maiores para frente e precisamos manter o valor original
            int el = array[i];

            // j começa no elemento anterior pois compara elemento atual com todos anteriores
            // até encontrar sua posição correta na parte ordenada
            int j = i - 1;

            if (DEBUG) {
                System.out.printf("\nInserindo elemento %d da posição %d:\n", el, i);
                printArrayWithMarkers(array, i, -1);
            }

            // Move elementos maiores que el uma posição à frente
            // O loop continua enquanto ainda há elementos para comparar na parte ordenada
            // e o elemento atual é menor que o elemento comparado
            while (j >= 0 && array[j] > el) {
                iterations++;

                if (DEBUG)
                    System.out.printf("  Movendo %d da posição %d para posição %d\n", array[j], j, j + 1);

                array[j + 1] = array[j];
                swaps++;
                j--;

                if (DEBUG)
                    printArrayWithMarkers(array, j + 1, j + 2);
            }

            // Se o elemento foi movido (j+1 != i), conta como uma troca
            if (j + 1 != i) {
                array[j + 1] = el;
                swaps++; // A inserção final conta como uma troca
            }

            if (DEBUG) {
                System.out.printf("  Elemento %d inserido na posição %d\n", array[i], j + 1);
                printArrayWithState(array, i);
            }
        }

        if (DEBUG) {
            System.out.println("Total de trocas: " + swaps);
            System.out.println("Total de iterações: " + iterations);
            System.out.print("\nVetor ordenado final: ");
            printArray(array);
        }
    }

    // Métodos auxiliares para visualização da execução do algoritmo
    private void printArrayWithMarkers(int[] array, int pos1, int pos2) {
        System.out.print("  Index:         ");
        for (int i = 0; i < array.length; i++)
            System.out.printf("%-4d", i);
        System.out.println();

        System.out.print("  Valores:       ");
        for (int value : array)
            System.out.printf("%-4d", value);
        System.out.println();

        System.out.print("  Comparações:   ");
        for (int i = 0; i < array.length; i++) {
            if (i == pos1 || i == pos2)
                System.out.print("*   ");
            else
                System.out.print("    ");
        }
        System.out.println();
    }

    private void printArrayWithState(int[] array, int sorted) {
        System.out.print("  Index:         ");
        for (int i = 0; i < array.length; i++)
            System.out.printf("%-4d", i);
        System.out.println();

        System.out.print("  Valores:       ");
        for (int value : array)
            System.out.printf("%-4d", value);
        System.out.println();

        System.out.print("  Parte Ordenada:");
        for (int i = 0; i < array.length; i++) {
            if (i <= sorted)
                System.out.print("*   ");
            else
                System.out.print("    ");
        }
        System.out.println("\n");
    }
}