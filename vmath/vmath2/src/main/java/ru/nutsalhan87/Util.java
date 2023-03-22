package ru.nutsalhan87;

import java.text.DecimalFormat;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Util {
    private static final DecimalFormat df = new DecimalFormat("###.###");
    public static final double epsilon = 1e-4;

    public static String doubleToStr(double num) {
        return df.format(num);
    }

    public static Function<Double, Double> getDerivative(Function<Double, Double> f, int level) {
        for (int i = 0; i < level; i++) {
            Function<Double, Double> fd = f;
            f = (arg) -> (fd.apply(arg + epsilon) - fd.apply(arg)) / epsilon;
        }

        return f;
    }

    public static BiFunction<Double, Double, Double> getDerivative(BiFunction<Double, Double, Double> f, int xLevel, int yLevel) {
        for (int i = 0; i < xLevel; i++) {
            BiFunction<Double, Double, Double> fd = f;
            f = (x, y) -> (fd.apply(x + epsilon, y) - fd.apply(x, y)) / epsilon;
        }
        for (int i = 0; i < yLevel; i++) {
            BiFunction<Double, Double, Double> fd = f;
            f = (x, y) -> (fd.apply(x, y + epsilon) - fd.apply(x, y)) / epsilon;
        }
        return f;
    }
}
