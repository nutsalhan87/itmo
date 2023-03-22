package ru.nutsalhan87.solve;

import java.util.function.Function;

public interface Integrate {
    double apply(Function<Double, Double> function, double left, double right, int steps);
}
