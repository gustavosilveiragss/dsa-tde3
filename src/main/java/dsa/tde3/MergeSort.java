package dsa.tde3;

public class MergeSort extends SortingAlgorithm {
    public MergeSort(boolean debug) {
        super(debug);
    }

    @Override
    public void sort(int[] array) {
        mergeSort(array, 0, array.length - 1, 0);
        if (DEBUG) {
            System.out.print("\nVetor ordenado final: ");
            printArray(array, 0, array.length - 1, array.length - 1, 0);
        }
    }

    // Método recursivo, array é o vetor a ser ordenado, left e right são os indices de início e fim do vetor/subvetor
    // depth é a profundidade da recursão, usada unicamente para identação da saída de debug
    private void mergeSort(int[] array, int left, int right, int depth) {
        // Se left >= right, o vetor tem no máximo um elemento e já está ordenado
        if (left >= right) return;

        // Calcula o index do elemento do meio do vetor
        int middle = (left + right) / 2;

        if (DEBUG) printArray(array, left, middle, right, depth);

        // Chama recursivamente mergeSort para os subvetores da esquerda e da direita
        mergeSort(array, left, middle, depth + 1); // Esquerda (do início até o meio)
        mergeSort(array, middle + 1, right, depth + 1); // Direita (do meio + 1 até o fim)
        merge(array, left, middle, right, depth); // Combina os subvetores ordenados
    }

    // Método que combina dois subvetores ordenados em um vetor ordenado
    // left é o index de início do primeiro subvetor, middle é o index do último elemento do primeiro subvetor
    private void merge(int[] array, int left, int middle, int right, int depth) {
        // Vetor temporário para armazenar os elementos ordenados
        int[] temp = new int[right - left + 1];
        int tempIdx = 0;
        int leftIdx = left;
        int rightIdx = middle + 1;

        // Enquanto houver elementos em ambos os subvetores, adiciona o menor elemento entre os subvetores ao vetor temporário
        while (leftIdx <= middle && rightIdx <= right)
            temp[tempIdx++] = (array[leftIdx] <= array[rightIdx]) ? array[leftIdx++] : array[rightIdx++];

        // Adicionar os elementos restantes dos subvetores ao vetor temporário
        while (leftIdx <= middle) temp[tempIdx++] = array[leftIdx++];
        while (rightIdx <= right) temp[tempIdx++] = array[rightIdx++];

        // Copia os elementos ordenados do vetor temporário para o vetor original
        for (leftIdx = 0; leftIdx < tempIdx; leftIdx++) array[left + leftIdx] = temp[leftIdx];

        if (DEBUG) {
            String offset = "  ".repeat(depth);
            System.out.printf("%sCombinando [%d-%d]: ", offset, left, right);
            printSubArray(array, left, right);
        }
    }

    // Métodos auxiliares para visualização da execução do algoritmo

    private void printArray(int[] array, int left, int middle, int right, int depth) {
        String offset = "  ".repeat(depth);
        System.out.printf("%sDividindo [%d-%d]: ", offset, left, right);
        printSubArray(array, left, right);
        System.out.printf("%s→ Esquerda [%d-%d]: ", offset, left, middle);
        printSubArray(array, left, middle);
        System.out.printf("%s→ Direita [%d-%d]: ", offset, middle + 1, right);
        printSubArray(array, middle + 1, right);
    }

    private void printSubArray(int[] array, int start, int end) {
        System.out.print("[");
        for (int i = start; i <= Math.min(end, start + 9); i++) {
            System.out.print(array[i]);
            if (i < end && i < start + 9) System.out.print(", ");
        }
        if (end > start + 9) System.out.print(", ...");
        System.out.println("]");
    }
}
