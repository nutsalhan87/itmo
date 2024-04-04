package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class MathTest {
    @Test
    void pow() {
        // TODO
    }

    @Nested
    class SinTest {
        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, Math.sin(Double.NaN));
            Assertions.assertEquals(Double.NaN, Math.sin(Double.POSITIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, Math.sin(Double.NEGATIVE_INFINITY));
        }

        @Test
        void zeroResult() {
            Assertions.assertEquals(0., Math.sin(0.));
            Assertions.assertEquals(-0., Math.sin(-0.));
            Assertions.assertNotEquals(-0., Math.sin(0.));
            Assertions.assertNotEquals(0., Math.sin(-0.));
        }

        @Test
        void sin() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * Constants.PI; x <= 100 * Constants.PI; x += Constants.PI / 10) {
                expected.add(java.lang.Math.sin(x));
                actual.add(Math.sin(x));
            }
            Assertions.assertArrayEquals(expected.stream().mapToDouble(i->i).toArray(),
                    actual.stream().mapToDouble(i->i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class CosTest {
        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, Math.cos(Double.NaN));
            Assertions.assertEquals(Double.NaN, Math.cos(Double.POSITIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, Math.cos(Double.NEGATIVE_INFINITY));
        }

        @Test
        void oneResult() {
            Assertions.assertEquals(1., Math.cos(0.));
            Assertions.assertEquals(1., Math.cos(-0.));
        }

        @Test
        void cos() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * Constants.PI; x <= 100 * Constants.PI; x += Constants.PI / 10) {
                expected.add(java.lang.Math.cos(x));
                actual.add(Math.cos(x));
            }
            Assertions.assertArrayEquals(expected.stream().mapToDouble(i->i).toArray(),
                    actual.stream().mapToDouble(i->i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class LnTest {
        @Test
        void zeroResult() {
            Assertions.assertEquals(0., Math.ln(1.));
        }

        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, Math.ln(Double.NaN));
            Assertions.assertEquals(Double.NaN, Math.ln(Double.NEGATIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, Math.ln(-5));
        }

        @Test
        void positiveInfResult() {
            Assertions.assertEquals(Double.POSITIVE_INFINITY, Math.ln(Double.POSITIVE_INFINITY));
        }

        @Test
        void negativeInfResult() {
            Assertions.assertEquals(Double.NEGATIVE_INFINITY, Math.ln(0.));
            Assertions.assertEquals(Double.NEGATIVE_INFINITY, Math.ln(-0.));
        }

        @Test
        void other() {
            LinkedList<Double> expected = new LinkedList<>();
            LinkedList<Double> actual = new LinkedList<>();
            for (double x = 1e-9; x <= 1e100; x *= 2) {
                expected.add(java.lang.Math.log(x));
                actual.add(Math.ln(x));
            }
            Assertions.assertArrayEquals(expected.stream().mapToDouble(i->i).toArray(),
                    actual.stream().mapToDouble(i->i).toArray(),
                    0.005);
        }
    }

    @Nested
    class LogTest {
        // сделать таблицу с полным перемножением +-inf, nan, 0, 1, -10 и 10 и ожидаемыми значениями
        // выделить в группы значений и сделать тесты, покрывающие эти группы

        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, Math.log(Double.NaN, 10));
            Assertions.assertEquals(Double.NaN, Math.log(10, Double.NaN));
            Assertions.assertEquals(Double.NaN, Math.log(Double.NEGATIVE_INFINITY, 10));
            Assertions.assertEquals(Double.NaN, Math.log(10, Double.NEGATIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, Math.log(-5, 10));
            Assertions.assertEquals(Double.NaN, Math.log(10, -5));
            Assertions.assertEquals(Double.NaN, Math.log(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        }

        @Test
        void positiveInfResult() {
            Assertions.assertEquals(Double.POSITIVE_INFINITY, Math.log(Double.POSITIVE_INFINITY, 10));
            Assertions.assertEquals(Double.POSITIVE_INFINITY, Math.log(10, 1));
        }

        @Test
        void negativeInfResult() {
            Assertions.assertEquals(Double.NEGATIVE_INFINITY, Math.log(0., 10));
        }

        @Test
        void other() {
            // TODO
        }
    }
}
