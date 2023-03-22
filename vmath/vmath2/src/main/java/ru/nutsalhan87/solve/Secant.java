package ru.nutsalhan87.solve;

import ru.nutsalhan87.Util;

import java.util.*;
import java.util.function.Function;

public class Secant implements Solve {
    @Override
    public AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve(double left, double right, double precision, Function<Double, Double> function) {
        if (function.apply(left) * function.apply(right) > 0) {
            throw new IllegalArgumentException("На границах интервала одинаковые знаки");
        }

        List<Map<String, Double>> actions = new ArrayList<>();
        Function<Double, Double> functionDer2 = Util.getDerivative(function, 2);
        double x0;
        double x1;
        if (function.apply(left) * functionDer2.apply(left) > 0) {
            x0 = left;
            x1 = x0 + precision;
        } else {
            x0 = right;
            x1 = x0 - precision;
        }
        double x2 = x1 - (x1 - x0) * function.apply(x1) / (function.apply(x1) - function.apply(x0));
        addAction(x2, x1, x0, function, actions);
        while (Math.abs(function.apply(x2)) > precision) {
            x0 = x1;
            x1 = x2;
            x2 = x1 - (x1 - x0) * function.apply(x1) / (function.apply(x1) - function.apply(x0));
            addAction(x2, x1, x0, function, actions);
        }

        return new AbstractMap.SimpleEntry<>(x2, actions);
    }

    private void addAction(double x2, double x1, double x0, Function<Double, Double> function, List<Map<String, Double>> actions) {
        Map<String, Double> action = new HashMap<>();
        action.putIfAbsent("x_k-1", x0);
        action.putIfAbsent("x_k", x1);
        action.putIfAbsent("x_k+1", x2);
        action.putIfAbsent("f(x_k+1)", function.apply(x2));
        action.putIfAbsent("|x_k+1 - x_k|", Math.abs(x2 - x1));
        actions.add(action);
    }
}
