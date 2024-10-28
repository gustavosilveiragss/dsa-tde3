package dsa.tde3;

import java.util.Random;

public class TestingUtils {
    public static int[] generateArray(Random rand, int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = rand.nextInt(1_000_000);
        return arr;
    }

    public static String formatTime(long ns) {
        if (ns < 1_000_000) return String.format("%s ns", formatDigits(ns));

        long msInt = ns / 1_000_000;

        if (msInt < 1_000)
            return String.format("%s ns (%s ms)", formatDigits(ns), formatDigits(msInt) + String.format("%.4f", (ns / 1_000_000.0) % 1).substring(1));

        long secInt = msInt / 1_000;
        if (secInt < 60)
            return String.format("%s ns (%s s)", formatDigits(ns), formatDigits(secInt) + String.format("%.4f", (ns / 1_000_000.0) / 1_000 % 1).substring(1));

        long minInt = secInt / 60;
        if (minInt < 60)
            return String.format("%s ns (%s min)", formatDigits(ns), formatDigits(minInt) + String.format("%.4f", secInt / 60.0 % 1).substring(1));

        long hrInt = minInt / 60;
        return String.format("%s ns (%s hr)", formatDigits(ns), formatDigits(hrInt) + String.format("%.4f", minInt / 60.0 % 1).substring(1));
    }

    public static String formatDigits(long n) {
        StringBuilder sb = new StringBuilder(Long.toString(n));
        for (int i = sb.length() - 3; i > 0; i -= 3) sb.insert(i, '_');
        return sb.toString();
    }
}
