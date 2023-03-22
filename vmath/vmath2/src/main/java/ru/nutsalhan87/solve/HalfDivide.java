package ru.nutsalhan87.solve;

import java.util.*;
import java.util.function.Function;

public class HalfDivide implements Solve {
    @Override
    public AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve(double left, double right, double precision, Function<Double, Double> function) {
        if (function.apply(left) * function.apply(right) > 0) {
            throw new IllegalArgumentException("На границах интервала одинаковые знаки");
        }

        List<Map<String, Double>> actions = new ArrayList<>();
        double center = (left + right) / 2;
        while(Math.abs(right - left) > precision) {
            center = (left + right) / 2;
            addAction(left, right, center, function, actions);
            if (function.apply(left) * function.apply(center) < 0) {
                right = center;
            } else {
                left = center;
            }
        }
        addAction(left, right, center, function, actions);
        return new AbstractMap.SimpleEntry<>(center, actions);
    }

    private void addAction(double left, double right, double center, Function<Double, Double> function, List<Map<String, Double>> actions) {
        Map<String, Double> action = new HashMap<>();
        action.putIfAbsent("a", left);
        action.putIfAbsent("b", right);
        action.putIfAbsent("x", center);
        action.putIfAbsent("f(a)", function.apply(left));
        action.putIfAbsent("f(b)", function.apply(right));
        action.putIfAbsent("f(x)", function.apply(center));
        action.putIfAbsent("|a-b|", Math.abs(right - left));
        actions.add(action);
    }
}
