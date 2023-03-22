package ru.nutsalhan87.solve;

public class Trapezoid {
    public static Integrate trapezoid() {
        return (function, left, right, steps) -> {
            double sum = 0;
            double step = (right - left) / steps;
            for (int i = 1; i < steps; ++i) {
                sum += function.apply(left + step * i);
            }
            sum += (function.apply(left) + function.apply(right)) / 2;
            sum *= step;
            return sum;
        };
    }
}
