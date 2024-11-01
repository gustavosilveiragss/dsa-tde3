package dsa.tde3;

public class BubbleSort extends SortingAlgorithm {
    public BubbleSort(boolean debug) {
        super(debug);
    }

    @Override
    public void sort(int[] array) {
        iterations = 0;
        swaps = 0;

        int end = array.length;

        // Continua ordenando enquanto houver trocas
        while (true) {
            iterations++;

            boolean swapped = false;

            if (DEBUG) {
                System.out.printf("\nPassagem do index 0 ao %d: ", (end - 1));
                printArray(array);
                System.out.println();
            }

            // Passagem pelo vetor, fazendo trocas quando necessário
            for (int i = 0; i < end - 1; i++) {
                iterations++;
                swapped = swap(array, i, swapped);
            }

            // Se não houve troca, vetor está ordenado
            if (!swapped) break;

            // Último elemento já está ordenado, reduz range de busca
            end--;
        }

        if (DEBUG) {
            System.out.print("Vetor ordenado final: ");
            printArray(array);
            System.out.println("Total de trocas: " + swaps);
            System.out.println("Total de iterações: " + iterations);
        }
    }

    // Método para realizar a troca entre i e o próximo elemento
    private boolean swap(int[] array, int i, boolean swapped) {
        if (array[i] <= array[i + 1]) return swapped;
        swap(array, i, i + 1);
        if (DEBUG) {
            System.out.printf("  Trocou %d e %d\n", array[i], array[i+1]);
            printArrayWithMarkers(array, i, i+1);
        }
        return true;
    }

    // Métodos auxiliares para visualização da execução do algoritmo
    private void printArrayWithMarkers(int[] array, int pos1, int pos2) {
        System.out.print("  Index:       ");
        for (int i = 0; i < array.length; i++)
            System.out.printf("%-4d", i);
        System.out.println();

        System.out.print("  Valores:     ");
        for (int value : array)
            System.out.printf("%-4d", value);
        System.out.println();

        System.out.print("  Comparações: ");
        for (int i = 0; i < array.length; i++) {
            if (i == pos1 || i == pos2)
                System.out.print("*   ");
            else
                System.out.print("    ");
        }
        System.out.println();
    }
}
