package dsa.tde3;

public abstract class SortingAlgorithm {
    protected static long iterations;
    protected static long swaps;

    protected final boolean DEBUG;

    public SortingAlgorithm(boolean debug) {
        DEBUG = debug;
    }

    public abstract void sort(int[] arr);

    // Método utilitário para verificar se vetor está ordenado
    protected static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i - 1] > arr[i]) return false;
        return true;
    }

    // Método utilitário para implementações que façam troca de elementos dessa forma (muito comum)
    protected static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        swaps++;
    }

    // Método utilitário apenas para debugar
    protected static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < Math.min(arr.length, 10); i++)
            System.out.print(arr[i] + (i < Math.min(arr.length - 1, 9) ? ", " : ""));
        System.out.println(arr.length > 10 ? ", ..." : "]");
    }
}
