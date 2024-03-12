package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SecTest {
    private final double eps = 0.00001;

    @Test
    public void zero() {
        Assertions.assertEquals(1d, Sec.sec(0), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {2 * Math.PI, -2 * Math.PI, 4 * Math.PI, -4 * Math.PI})
    public void minExtremumBeyondPi(double x) {
        Assertions.assertEquals(1d, Sec.sec(x), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, Math.PI, 3 * Math.PI, -3 * Math.PI})
    public void maxExtremumBeyondPi(double x) {
        Assertions.assertEquals(-1d, Sec.sec(x), eps);
    }

    @Test
    public void nearPiDiv2() {
        Assertions.assertEquals(100_000, Sec.sec(Math.PI / 2 - 0.00001), eps);
        Assertions.assertEquals(100_000, Sec.sec(-Math.PI / 2 + 0.00001), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {2 * Math.PI, -2 * Math.PI, 4 * Math.PI, -4 * Math.PI})
    public void nearPiDiv2ModifiedPositive(double x) {
        Assertions.assertEquals(100_000, Sec.sec(x + Math.PI / 2 - 0.00001), eps);
        Assertions.assertEquals(100_000, Sec.sec(x - Math.PI / 2 + 0.00001), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, Math.PI, 3 * Math.PI, -3 * Math.PI})
    public void nearPiDiv2ModifiedNegative(double x) {
        Assertions.assertEquals(-100_000, Sec.sec(x + Math.PI / 2 - 0.00001), eps);
        Assertions.assertEquals(-100_000, Sec.sec(x - Math.PI / 2 + 0.00001), eps);
    }
}
