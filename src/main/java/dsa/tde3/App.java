package dsa.tde3;

import java.io.*;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    private static final boolean DEBUG = false;
    private static final int[] SIZES = DEBUG ? new int[]{10} : new int[]{10, 1_000, 10_000, 100_000, 1_000_000};
    private static final int ROUNDS = DEBUG ? 1 : 5;
    private static final Random rand = new Random(64); // Seed fixa para garantir reprodutibilidade
    private static final BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>();
    private static boolean testsCompleted = false;
    private static final String CSV_FILE = "analysis/results1.csv";

    static {
        try (FileWriter fw = new FileWriter(CSV_FILE)) {
            fw.write("algorithm,array_size,round,execution_time_ns,execution_time_formatted,swaps,iterations\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread csvWriter = new Thread(() -> {
            try (FileWriter fw = new FileWriter(CSV_FILE, true)) {
                while (!testsCompleted || !resultQueue.isEmpty()) {
                    String result = resultQueue.poll();
                    if (result != null) {
                        fw.write(result);
                        fw.flush();
                    } else {
                        System.gc();
                        Thread.sleep(100);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        csvWriter.setDaemon(true);
        csvWriter.start();
    }

    private static void logResult(String algorithm, int size, int round, long executionTime, long swaps, long iterations) {
        String formattedTime = TestingUtils.formatTime(executionTime);

        String csvLine = String.format("%s,%d,%d,%d,%s,%d,%d\n",
                algorithm,
                size,
                round,
                executionTime,
                formattedTime,
                swaps,
                iterations
        );

        resultQueue.offer(csvLine);
    }

    private static void testSortingAlgo(SortingAlgorithm sortingAlgorithm) {
        String algorithmName = sortingAlgorithm.getClass().getSimpleName();
        System.out.println("Testando " + algorithmName + "...");

        for (int size : SIZES) {
            long totalTime = 0;
            long totalIterations = 0;
            long totalSwaps = 0;

            for (int round = 0; round < ROUNDS; round++) {
                int[] arr = DEBUG ? new int[]{5, 2, 4, 6, 1, 3, 8, 6, 9, 7} : TestingUtils.generateArray(rand, size);

                long startTime = System.nanoTime();
                sortingAlgorithm.sort(arr);
                long endTime = System.nanoTime();

                if (!SortingAlgorithm.isSorted(arr))
                    throw new AssertionError("Array não está ordenado!");

                long roundTime = endTime - startTime;
                totalTime += roundTime;

                totalIterations += SortingAlgorithm.iterations;
                totalSwaps += SortingAlgorithm.swaps;

                logResult(algorithmName, size, round + 1, roundTime, SortingAlgorithm.swaps, SortingAlgorithm.iterations);

                System.out.printf("Tamanho: %d, Round: %d, Tempo: %s, Iterações: %d, Trocas: %d\n", size, round + 1, TestingUtils.formatTime(roundTime), SortingAlgorithm.iterations, SortingAlgorithm.swaps);
            }

            System.out.printf("Tempo de execução médio para tamanho %d: %s\n", size, TestingUtils.formatTime(totalTime / ROUNDS));
            System.out.printf("Número médio de iterações para tamanho %d: %d\n", size, totalIterations / ROUNDS);
            System.out.printf("Número médio de trocas para tamanho %d: %d\n\n", size, totalSwaps / ROUNDS);
        }
    }

    public static void main(String[] args) {
//        testSortingAlgo(new BogoSort(DEBUG));
        testSortingAlgo(new InsertionSort(DEBUG));
        testSortingAlgo(new MergeSort(DEBUG));
        testSortingAlgo(new ShellSort(DEBUG));
        testSortingAlgo(new CocktailSort(DEBUG));
        testSortingAlgo(new BubbleSort(DEBUG));
        testsCompleted = true;
    }
}