package ru.nutsalhan87.system;

import java.util.Map;
import java.util.function.BiFunction;

public interface SystemSolve {
    Map<String, Double> solve(double x0, double y0, double precision, BiFunction<Double, Double, Double> functionF, BiFunction<Double, Double, Double> functionS);
}
