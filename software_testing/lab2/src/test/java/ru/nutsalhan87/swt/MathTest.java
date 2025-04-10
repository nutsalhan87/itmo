package ru.nutsalhan87.swt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nutsalhan87.swt.math.*;
import ru.nutsalhan87.swt.util.CSV;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static java.lang.Double.*;
import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MathTest {
    @BeforeAll
    public static void createOutCsvDir() {
        var csvDir = new File("out_csv");
        csvDir.mkdir();
    }

    abstract static class FunctionTest {
        protected final Function f;
        protected final Function fMocked;
        protected final Set<Double> tableValues;
        protected FunctionTest(Function f) throws IOException {
            var p = MockedFactory.mock(f);
            this.f = f;
            this.fMocked = p.f();
            this.tableValues = p.s();
        }

        @Test
        void other() throws IOException {
            var csv = new CSV<>(f);
            for (double x : tableValues) {
                var expected = fMocked.apply(x);
                if (isFinite(expected) && expected > 1e12) {
                    expected = NaN;
                }
                var actual = f.apply(x);
                if (!isFinite(actual)) {
                    actual = NaN;
                }
                csv.saveComputed(x);
                assertEquals(expected, actual, 0.005, "x = " + x);
            }
            csv.saveToFile();
        }
    }

    @Nested
    class SinTest extends FunctionTest {
        SinTest() throws IOException {
            super(new Sin());
        }

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, f.apply(x));
        }

        @Test
        void zeroResult() {
            assertEquals(0., f.apply(0.));
            assertEquals(-0., f.apply(-0.));
            assertNotEquals(-0., f.apply(0.));
            assertNotEquals(0., f.apply(-0.));
        }
    }

    @Nested
    class CosTest extends FunctionTest {
        CosTest() throws IOException {
            super(new Cos());
        }

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, f.apply(x));
        }

        @Test
        void oneResult() {
            assertEquals(1., f.apply(0.));
            assertEquals(1., f.apply(-0.));
        }
    }

    @Nested
    class TgTest extends FunctionTest {
        TgTest() throws IOException {
            super(new Tg());
        }

        @ParameterizedTest
        @ValueSource(doubles = {NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, f.apply(x));
        }

        @Test
        void zeroResult() {
            assertEquals(0., f.apply(0.));
            assertEquals(-0., f.apply(-0.));
            assertNotEquals(-0., f.apply(0.));
            assertNotEquals(0., f.apply(-0.));
        }

        @ParameterizedTest
        @ValueSource(doubles = {3 * PI / 2, PI / 2, -PI / 2, -3 * PI / 2})
        void infResult(double x) {
            assertEquals(POSITIVE_INFINITY, f.apply(x));
        }
    }

    @Nested
    class SecTest extends FunctionTest {
        SecTest() throws IOException {
            super(new Sec());
        }

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, f.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI / 2, -PI / 2, PI / 2, 3 * PI / 2})
        void infResult(double x) {
            assertEquals(POSITIVE_INFINITY, f.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-4 * PI, -2 * PI, 0, 2 * PI, 4 * PI})
        public void minExtremumBeyondPi(double x) {
            assertEquals(1., f.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI, -PI, PI, 3 * PI})
        public void maxExtremumBeyondPi(double x) {
            assertEquals(-1., f.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-4 * PI, -2 * PI, 0, 2 * PI, 4 * PI})
        public void nearPiDiv2Positive(double x) {
            assertEquals(10_000, f.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(10_000, f.apply(x - PI / 2 + 0.0001), 0.5);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI, -PI, PI, 3 * PI})
        public void nearPiDiv2Negative(double x) {
            assertEquals(-10_000, f.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(-10_000, f.apply(x - PI / 2 + 0.0001), 0.5);
        }
    }

    @Nested
    class CosecTest extends FunctionTest {
        CosecTest() throws IOException {
            super(new Cosec());
        }

        @ParameterizedTest
        @ValueSource(doubles = {NaN, POSITIVE_INFINITY, NEGATIVE_INFINITY})
        void nanResult(double x) {
            assertEquals(NaN, f.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-3 * PI, -2 * PI, -PI, PI, 2 * PI, 3 * PI})
        void infResult(double x) {
            assertEquals(POSITIVE_INFINITY, f.apply(x));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-7 * PI / 2, -3 * PI / 2, PI / 2, 5 * PI / 2, 13 * PI / 2})
        public void minExtremumBeyondPi(double x) {
            assertEquals(1., f.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-5 * PI / 2, -PI / 2, 3 * PI / 2, 7 * PI / 2})
        public void maxExtremumBeyondPi(double x) {
            assertEquals(-1., f.apply(x), Constants.EPSILON);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-7 * PI / 2, -3 * PI / 2, PI / 2, 5 * PI / 2, 13 * PI / 2})
        public void nearPiDiv2Positive(double x) {
            assertEquals(10_000, f.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(10_000, f.apply(x - PI / 2 + 0.0001), 0.5);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-5 * PI / 2, -PI / 2, 3 * PI / 2, 7 * PI / 2})
        public void nearPiDiv2Negative(double x) {
            assertEquals(-10_000, f.apply(x + PI / 2 - 0.0001), 0.5);
            assertEquals(-10_000, f.apply(x - PI / 2 + 0.0001), 0.5);
        }
    }

    @Nested
    class LnTest extends FunctionTest {
        LnTest() throws IOException {
            super(new Ln());
        }

        @Test
        void zeroResult() {
            assertEquals(0., f.apply(1.));
        }

        @Test
        void nanResult() {
            assertEquals(NaN, f.apply(NaN));
            assertEquals(NaN, f.apply(NEGATIVE_INFINITY));
            assertEquals(NaN, f.apply(-5.));
        }

        @Test
        void positiveInfResult() {
            assertEquals(POSITIVE_INFINITY, f.apply(POSITIVE_INFINITY));
        }

        @Test
        void negativeInfResult() {
            assertEquals(NEGATIVE_INFINITY, f.apply(0.));
            assertEquals(NEGATIVE_INFINITY, f.apply(-0.));
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
    }

    @Nested
    class Log3Test extends FunctionTest {
        protected Log3Test() throws IOException {
            super(new Log(3));
        }
    }

    @Nested
    class Log5Test extends FunctionTest {
        protected Log5Test() throws IOException {
            super(new Log(5));
        }
    }
}
