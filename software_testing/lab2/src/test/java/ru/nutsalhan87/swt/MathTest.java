package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
        static Math math;
        @BeforeAll
        static void init(){
            math = new Math(new Pow(), new Sin(), new Cos(), new Ln(), new Log(), new Tan(), new Csc(), new Sec());
        }
        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, math.sin(Double.NaN));
            Assertions.assertEquals(Double.NaN, math.sin(Double.POSITIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, math.sin(Double.NEGATIVE_INFINITY));
        }

        @Test
        void zeroResult() {
            Assertions.assertEquals(0., math.sin(0.));
            Assertions.assertEquals(-0., math.sin(-0.));
            Assertions.assertNotEquals(-0., math.sin(0.));
            Assertions.assertNotEquals(0., math.sin(-0.));
        }

        @Test
        void sin() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * Constants.PI; x <= 100 * Constants.PI; x += Constants.PI / 10) {
                expected.add(java.lang.Math.sin(x));
                actual.add(math.sin(x));
            }
            Assertions.assertArrayEquals(expected.stream().mapToDouble(i->i).toArray(),
                    actual.stream().mapToDouble(i->i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class CosTest {
        static Math math;
        @BeforeAll
        static void init(){
            math = new Math(new Pow(), new Sin(), new Cos(), new Ln(), new Log(), new Tan(), new Csc(), new Sec());
        }
        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, math.cos(Double.NaN));
            Assertions.assertEquals(Double.NaN, math.cos(Double.POSITIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, math.cos(Double.NEGATIVE_INFINITY));
        }

        @Test
        void oneResult() {
            Assertions.assertEquals(1., math.cos(0.));
            Assertions.assertEquals(1., math.cos(-0.));
        }

        @Test
        void cos() {
            List<Double> expected = new ArrayList<>();
            List<Double> actual = new ArrayList<>();
            for (double x = -100 * Constants.PI; x <= 100 * Constants.PI; x += Constants.PI / 10) {
                expected.add(java.lang.Math.cos(x));
                actual.add(math.cos(x));
            }
            Assertions.assertArrayEquals(expected.stream().mapToDouble(i->i).toArray(),
                    actual.stream().mapToDouble(i->i).toArray(),
                    Constants.EPSILON);
        }
    }

    @Nested
    class LnTest {
        static Math math;
        @BeforeAll
        static void init(){
            math = new Math(new Pow(), new Sin(), new Cos(), new Ln(), new Log(), new Tan(), new Csc(), new Sec());
        }
        @Test
        void zeroResult() {
            Assertions.assertEquals(0., math.ln(1.));
        }

        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, math.ln(Double.NaN));
            Assertions.assertEquals(Double.NaN, math.ln(Double.NEGATIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, math.ln(-5));
        }

        @Test
        void positiveInfResult() {
            Assertions.assertEquals(Double.POSITIVE_INFINITY, math.ln(Double.POSITIVE_INFINITY));
        }

        @Test
        void negativeInfResult() {
            Assertions.assertEquals(Double.NEGATIVE_INFINITY, math.ln(0.));
            Assertions.assertEquals(Double.NEGATIVE_INFINITY, math.ln(-0.));
        }

        @Test
        void other() {
            LinkedList<Double> expected = new LinkedList<>();
            LinkedList<Double> actual = new LinkedList<>();
            for (double x = 1e-9; x <= 1e100; x *= 2) {
                expected.add(java.lang.Math.log(x));
                actual.add(math.ln(x));
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

        static Math math;
        @BeforeAll
        static void init(){
            math = new Math(new Pow(), new Sin(), new Cos(), new Ln(), new Log(), new Tan(), new Csc(), new Sec());
        }
        @Test
        void nanResult() {
            Assertions.assertEquals(Double.NaN, math.log(Double.NaN, 10));
            Assertions.assertEquals(Double.NaN, math.log(10, Double.NaN));
            Assertions.assertEquals(Double.NaN, math.log(Double.NEGATIVE_INFINITY, 10));
            Assertions.assertEquals(Double.NaN, math.log(10, Double.NEGATIVE_INFINITY));
            Assertions.assertEquals(Double.NaN, math.log(-5, 10));
            Assertions.assertEquals(Double.NaN, math.log(10, -5));
            Assertions.assertEquals(Double.NaN, math.log(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        }

        @Test
        void positiveInfResult() {
            Assertions.assertEquals(Double.POSITIVE_INFINITY, math.log(Double.POSITIVE_INFINITY, 10));
            Assertions.assertEquals(Double.POSITIVE_INFINITY, math.log(10, 1));
        }

        @Test
        void negativeInfResult() {
            Assertions.assertEquals(Double.NEGATIVE_INFINITY, math.log(0., 10));
        }

        @Test
        void other() {
            // TODO
        }
    }
}
