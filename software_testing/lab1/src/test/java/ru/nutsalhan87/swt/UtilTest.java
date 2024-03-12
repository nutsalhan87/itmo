package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
