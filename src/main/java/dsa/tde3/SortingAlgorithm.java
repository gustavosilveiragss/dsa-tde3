package dsa.tde3;

public abstract class SortingAlgorithm {
    public abstract void sort(int[] arr);

    protected boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i - 1] > arr[i]) return false;
        return true;
    }

    // Debugging purposes only
    protected void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < Math.min(arr.length, 10); i++)
            System.out.print(arr[i] + (i < Math.min(arr.length - 1, 9) ? ", " : ""));
        System.out.println(arr.length > 10 ? ", ..." : "]");
    }
}
