package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nutsalhan87.swt.math.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Double.*;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

public class MathTest {
    @Nested
    class SinTest {
        private final Sin sin = new Sin();

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, sin.apply(x));
        }

        @Test
        void zeroResult() {
            assertEquals(0., sin.apply(0.));
            assertEquals(-0., sin.apply(-0.));
            assertNotEquals(-0., sin.apply(0.));
            assertNotEquals(0., sin.apply(-0.));
        }

        @Test
        void other() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * PI; x <= 100 * PI; x += PI / 10) {
                expected.add(sin(x));
                actual.add(sin.apply(x));
            }
            assertArrayEquals(expected.stream().mapToDouble(i -> i).toArray(),
                    actual.stream().mapToDouble(i -> i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class CosTest {
        private final Cos cos = new Cos();

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, cos.apply(x));
        }

        @Test
        void oneResult() {
            assertEquals(1., cos.apply(0.));
            assertEquals(1., cos.apply(-0.));
        }

        @Test
        void other() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * PI; x <= 100 * PI; x += PI / 10) {
                expected.add(cos(x));
                actual.add(cos.apply(x));
            }
            assertArrayEquals(expected.stream().mapToDouble(i -> i).toArray(),
                    actual.stream().mapToDouble(i -> i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class TgTest {
        private final Tg tg = new Tg();

        @ParameterizedTest
        @ValueSource(doubles = {NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, tg.apply(x));
        }

        @Test
        void zeroResult() {
            assertEquals(0., tg.apply(0.));
            assertEquals(-0., tg.apply(-0.));
            assertNotEquals(-0., tg.apply(0.));
            assertNotEquals(0., tg.apply(-0.));
        }

        @ParameterizedTest
        @ValueSource(doubles = {3 * PI / 2, PI / 2, -PI / 2, -3 * PI / 2})
        void infResult(double x) {
            assertEquals(POSITIVE_INFINITY, tg.apply(x));
        }

        @Test
        void other() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * PI; x <= 100 * PI; x += PI / 3) {
                expected.add(tan(x));
                actual.add(tg.apply(x));
            }
            assertArrayEquals(expected.stream().mapToDouble(i -> i).toArray(),
                    actual.stream().mapToDouble(i -> i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class SecTest {
        private final Sec sec = new Sec();

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, sec.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI / 2, -PI / 2, PI / 2, 3 * PI / 2})
        void infResult(double x) {
            assertEquals(POSITIVE_INFINITY, sec.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-4 * PI, -2 * PI, 0, 2 * PI, 4 * PI})
        public void minExtremumBeyondPi(double x) {
            assertEquals(1., sec.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI, -PI, PI, 3 * PI})
        public void maxExtremumBeyondPi(double x) {
            assertEquals(-1., sec.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-4 * PI, -2 * PI, 0, 2 * PI, 4 * PI})
        public void nearPiDiv2Positive(double x) {
            assertEquals(10_000, sec.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(10_000, sec.apply(x - PI / 2 + 0.0001), 0.5);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI, -PI, PI, 3 * PI})
        public void nearPiDiv2Negative(double x) {
            assertEquals(-10_000, sec.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(-10_000, sec.apply(x - PI / 2 + 0.0001), 0.5);
        }
    }

    @Nested
    class CosecTest {
        private final Cosec cosec = new Cosec();

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, cosec.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI, -2 * PI, -PI, PI, 2 * PI, 3 * PI})
        void infResult(double x) {
            assertEquals(POSITIVE_INFINITY, cosec.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-7 * PI / 2, -3 * PI / 2, PI / 2, 5 * PI / 2, 13 * PI / 2})
        public void minExtremumBeyondPi(double x) {
            assertEquals(1., cosec.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-5 * PI / 2, -PI / 2, 3 * PI / 2, 7 * PI / 2})
        public void maxExtremumBeyondPi(double x) {
            assertEquals(-1., cosec.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-7 * PI / 2, -3 * PI / 2, PI / 2, 5 * PI / 2, 13 * PI / 2})
        public void nearPiDiv2Positive(double x) {
            assertEquals(10_000, cosec.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(10_000, cosec.apply(x - PI / 2 + 0.0001), 0.5);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-5 * PI / 2, -PI / 2, 3 * PI / 2, 7 * PI / 2})
        public void nearPiDiv2Negative(double x) {
            assertEquals(-10_000, cosec.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(-10_000, cosec.apply(x - PI / 2 + 0.0001), 0.5);
        }
    }

    @Nested
    class LnTest {
        private final Ln ln = new Ln();

        @Test
        void zeroResult() {
            assertEquals(0., ln.apply(1.));
        }

        @Test
        void nanResult() {
            assertEquals(NaN, ln.apply(NaN));
            assertEquals(NaN, ln.apply(NEGATIVE_INFINITY));
            assertEquals(NaN, ln.apply(-5.));
        }

        @Test
        void positiveInfResult() {
            assertEquals(POSITIVE_INFINITY, ln.apply(POSITIVE_INFINITY));
        }

        @Test
        void negativeInfResult() {
            assertEquals(NEGATIVE_INFINITY, ln.apply(0.));
            assertEquals(NEGATIVE_INFINITY, ln.apply(-0.));
        }

        @Test
        void other() {
            LinkedList<Double> expected = new LinkedList<>();
            LinkedList<Double> actual = new LinkedList<>();
            for (double x = 1e-9; x <= 1e100; x *= 2) {
                expected.add(log(x));
                actual.add(ln.apply(x));
            }
            assertArrayEquals(expected.stream().mapToDouble(i -> i).toArray(),
                    actual.stream().mapToDouble(i -> i).toArray(),
                    0.005);
        }
    }

    @Nested
    class LogTest {
        @Test
        void nanResult() {
            assertEquals(NaN, new Log(10).apply(NaN));
            assertEquals(NaN, new Log(NaN).apply(10.));
            assertEquals(NaN, new Log(NEGATIVE_INFINITY).apply(10.));
            assertEquals(NaN, new Log(-5).apply(10.));
            assertEquals(NaN, new Log(10.).apply(-5.));
            assertEquals(NaN, new Log(10.).apply(NEGATIVE_INFINITY));
            assertEquals(NaN, new Log(POSITIVE_INFINITY).apply(POSITIVE_INFINITY));
        }

        @Test
        void positiveInfResult() {
            assertEquals(POSITIVE_INFINITY, new Log(10.).apply(POSITIVE_INFINITY));
            assertEquals(POSITIVE_INFINITY, new Log(1.).apply(10.));
        }

        @Test
        void negativeInfResult() {
            assertEquals(NEGATIVE_INFINITY, new Log(10.).apply(0.));
        }

        @Test
        void other() {
            LinkedList<Double> expected = new LinkedList<>();
            LinkedList<Double> actual = new LinkedList<>();
            for (double x = 1e-5; x <= 1e100; x *= 2) {
                for (double n = 1e-4; n <= 1e10; n *= 2) {
                    if (n == 1) {
                        continue;
                    }
                    expected.add(log(x) / log(n));
                    actual.add(new Log(n).apply(x));
                }
            }
            assertArrayEquals(expected.stream().mapToDouble(i -> i).toArray(),
                    actual.stream().mapToDouble(i -> i).toArray(),
                    0.005);
        }
    }
}
