package ru.nutsalhan87.solve;

import java.util.function.BiFunction;

public class Rectangle {
    public static Integrate left() {
        return rectangle((left, right) -> left);
    }

    public static Integrate center() {
        return rectangle((left, right) -> (left + right) / 2);
    }

    public static Integrate right() {
        return rectangle((left, right) -> right);
    }

    private static Integrate rectangle(BiFunction<Double, Double, Double> borderSelector) {
        return (function, left, right, steps) -> {
            double sum = 0;
            double step = (right - left) / steps;
            for (int i = 0; i < steps; ++i) {
                sum += borderSelector.apply(function.apply(left + step * i), function.apply(left + step * (i + 1)));
            }
            sum *= step;
            return sum;
        };
    }
}
