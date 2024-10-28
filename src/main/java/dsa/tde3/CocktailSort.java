package dsa.tde3;

public class CocktailSort extends SortingAlgorithm {
    public CocktailSort(boolean debug) {
        super(debug);
    }

    @Override
    public void sort(int[] array) {
        // Inicia passagens pelo index zero até o último
        int start = 0;
        int end = array.length;
        boolean swapped = true;

        // Enquanto ocorrerem trocas, o vetor ainda está sendo ordenado
        while (swapped) {
            // Reinicia indicador para primeira passagem
            swapped = false;

            if (DEBUG) {
                System.out.printf("\nPassagem LTR do index %d ao %d: ", start, (end - 1));
                printArray(array);
                System.out.println();
            }

            // Passagem LTR do vetor, fazendo trocas, caso necessário
            for (int i = start; i < end - 1; i++)
                swapped = swap(array, i, swapped);

            // Se não houve troca, já está ordenado
            if (!swapped) break;

            // Último elemento já vai ser o maior, não precisamos verificar novamente
            end--;

            // Reinicia flag para próxima passagem (RTL)
            swapped = false;

            if (DEBUG) {
                System.out.printf("\nPassagem RTL do index %d ao %d: ", start, (end - 1));
                printArray(array);
                System.out.println();
            }

            // Passagem RTL do vetor, fazendo trocas, caso necessário
            // Inicia do penúltimo elemento pois método swap compara com o próximo elemento sempre
            for (int i = end - 2; i >= start; i--)
                swapped = swap(array, i, swapped);

            // Primeiro elemento necessariamente será o menor,
            start++;
        }

        if (DEBUG) {
            System.out.print("Vetor ordenado final: ");
            printArray(array);
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