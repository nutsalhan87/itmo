package ru.nutsalhan87.solve;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Solve {
    AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve(double left, double right, double precision, Function<Double, Double> function);
}
