package ru.nutsalhan87.solve;

import java.util.*;
import java.util.function.Function;

public class Chord implements Solve {
    @Override
    public AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve(double left, double right, double precision, Function<Double, Double> function) {
        if (function.apply(left) * function.apply(right) > 0) {
            throw new IllegalArgumentException("На границах интервала одинаковые знаки");
        }

        return solvedUnfixed(left, right, precision, function);
    }

    private AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solvedUnfixed(double left, double right, double precision, Function<Double, Double> function) {
        List<Map<String, Double>> actions = new ArrayList<>();
        double x = left - (right - left) * function.apply(left) / (function.apply(right) - function.apply(left));
        double xOld = x;
        while (Math.abs(right - left) > precision) {
            x = left - (right - left) * function.apply(left) / (function.apply(right) - function.apply(left));
            addAction(left, right, x, xOld, function, actions);
            if (function.apply(left) * function.apply(x) < 0) {
                right = x;
            } else {
                left = x;
            }
            xOld = x;
        }
        addAction(left, right, x, xOld, function, actions);
        return new AbstractMap.SimpleEntry<>(x, actions);
    }

    private void addAction(double left, double right, double x, double xOld, Function<Double, Double> function, List<Map<String, Double>> actions) {
        Map<String, Double> action = new HashMap<>();
        action.putIfAbsent("a", left);
        action.putIfAbsent("b", right);
        action.putIfAbsent("x", x);
        action.putIfAbsent("f(a)", function.apply(left));
        action.putIfAbsent("f(b)", function.apply(right));
        action.putIfAbsent("f(x)", function.apply(x));
        action.putIfAbsent("|x_(k+1)-x_k|", Math.abs(x - xOld));
        actions.add(action);
    }
}
