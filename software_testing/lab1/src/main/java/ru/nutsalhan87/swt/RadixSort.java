package ru.nutsalhan87.swt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RadixSort {
    private static final int[] tenPower = {1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000, 100_000_000, 1_000_000_000};

    public static int[] sort(int[] source) {
        int maxDigits = Integer.valueOf(Arrays.stream(source).max().orElse(0)).toString().length();
        List<Integer> sorted = new ArrayList<>(Arrays.stream(source).boxed().toList());
        for (int i = 0; i < maxDigits; ++i) {
            List<List<Integer>> radix = new ArrayList<>(10);
            for (int j = 0; j < 10; ++j) {
                radix.add(new ArrayList<>());
            }
            for (int num : sorted) {
                radix.get((num / tenPower[i]) % 10).add(num);
            }
            sorted.clear();
            radix.forEach(sorted::addAll);
        }

        return sorted.stream().mapToInt(v -> v).toArray();
    }
}
