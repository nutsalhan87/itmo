package ru.nutsalhan87.solve;

public class Simpson {
    public static Integrate simpson() {
        return (function, left, right, steps) -> {
            double sum = 0;
            double step = (right - left) / steps;
            for (int i = 1; i < steps; ++i) {
                if (i % 2 == 1) {
                    sum += 4 * function.apply(left + step * i);
                } else {
                    sum += 2 * function.apply(left + step * i);
                }
            }
            sum += function.apply(left) + function.apply(right);
            sum *= step / 3;
            return sum;
        };
    }
}
