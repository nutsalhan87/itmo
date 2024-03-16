package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class UtilTest {
    @Test
    void enumerate() {
        Integer[] array = {5, 4, 3, 2, 1};
        var enumerated = Util.enumerate(Arrays.stream(array)).toArray();
        var expectedEnumerated = List.of(
                new Util.Enumerated<>(0, 5),
                new Util.Enumerated<>(1, 4),
                new Util.Enumerated<>(2, 3),
                new Util.Enumerated<>(3, 2),
                new Util.Enumerated<>(4, 1)
        );
        Assertions.assertArrayEquals(expectedEnumerated.toArray(), enumerated);
    }

    @Test
    void byteToHex() {
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        var actual = IntStream.range(0, 256).mapToObj(i -> Util.toHex(Integer.valueOf(i).byteValue())).toList();
        var expected = new ArrayList<String>();
        for (var a: hex) {
            for (var b : hex) {
                expected.add(a + b);
            }
        }
        Assertions.assertArrayEquals(expected.toArray(new String[]{}), actual.toArray(new String[]{}));
    }
}
