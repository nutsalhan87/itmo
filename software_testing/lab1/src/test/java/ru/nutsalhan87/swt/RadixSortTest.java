package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RadixSortTest {
    @Test
    void empty() {
        int[] source = {};
        Assertions.assertArrayEquals(source, RadixSort.sort(source));
    }

    @Test
    void oneElement() {
        int[] source = {5};
        Assertions.assertArrayEquals(source, RadixSort.sort(source));
    }

    @Test
    void multipleElements() {
        int[] source = {906, 204, 868, 40, 463, 68, 515, 960, 913, 362, 338, 104, 31, 505, 986, 737, 97, 0, 549};
        int[] expected = Arrays.stream(source).sorted().toArray();
        Assertions.assertArrayEquals(expected, RadixSort.sort(source));
    }
}
