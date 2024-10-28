package dsa.tde3;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    private static final boolean DEBUG = false;
    private static final int[] SIZES = DEBUG ? new int[]{10} : new int[]{10, 1_000, 10_000, 100_000, 1_000_000};
    private static final int ROUNDS = DEBUG ? 1 : 5;
    private static final Random rand = new Random(64); // Seed fixa para garantir reprodutibilidade
    private static final BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>();
    private static volatile boolean testsCompleted = false;
    private static final String CSV_FILE = "analysis/results1.csv";

    static {
        try (FileWriter fw = new FileWriter(CSV_FILE)) {
            fw.write("algorithm,array_size,round,execution_time_ns,execution_time_formatted\n");
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
                        // evita consumo excessivo de CPU
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

    private static void logResult(String algorithm, int size, int round, long executionTime) {
        String formattedTime = TestingUtils.formatTime(executionTime);

        String csvLine = String.format("%s,%d,%d,%d,%s\n",
                algorithm,
                size,
                round,
                executionTime,
                formattedTime
        );

        resultQueue.offer(csvLine);
    }

    private static void testSortingAlgo(SortingAlgorithm sortingAlgorithm) {
        String algorithmName = sortingAlgorithm.getClass().getSimpleName();
        System.out.println("Testando " + algorithmName + "...");

        for (int size : SIZES) {
            long totalTime = 0;

            for (int round = 0; round < ROUNDS; round++) {
                int[] arr = DEBUG ? new int[]{5, 2, 4, 6, 1, 3, 8, 6, 9, 7} : TestingUtils.generateArray(rand, size);

                long startTime = System.nanoTime();
                sortingAlgorithm.sort(arr);
                long endTime = System.nanoTime();

                if (!sortingAlgorithm.isSorted(arr))
                    throw new AssertionError("Array não está ordenado!");

                long roundTime = endTime - startTime;
                totalTime += roundTime;

                logResult(algorithmName, size, round + 1, roundTime);

                System.out.printf("Tamanho: %d, Round: %d, Tempo: %s%n", size, round + 1, TestingUtils.formatTime(roundTime));
            }

            System.out.printf("Tempo médio para tamanho %d: %s%n%n", size, TestingUtils.formatTime(totalTime / ROUNDS));
        }
    }

    public static void main(String[] args) {
        testSortingAlgo(new BogoSort(DEBUG));
//        testSortingAlgo(new InsertionSort(DEBUG));
//        testSortingAlgo(new MergeSort(DEBUG));
//        testSortingAlgo(new ShellSort(DEBUG));
//        testSortingAlgo(new CocktailSort(DEBUG));
//        testSortingAlgo(new BubbleSort(DEBUG));

        testsCompleted = true;
    }
}