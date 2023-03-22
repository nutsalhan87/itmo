package ru.nutsalhan87.solve;

import ru.nutsalhan87.Util;

import java.util.*;
import java.util.function.Function;

public class Newton implements Solve {
    @Override
    public AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve(double left, double right, double precision, Function<Double, Double> function) {
        if (function.apply(left) * function.apply(right) > 0) {
            throw new IllegalArgumentException("На границах интервала одинаковые знаки");
        }

        List<Map<String, Double>> actions = new ArrayList<>();
        Function<Double, Double> functionDer1 = Util.getDerivative(function, 1);
        Function<Double, Double> functionDer2 = Util.getDerivative(function, 2);
        double x;
        if (function.apply(left) * functionDer2.apply(left) > 0) {
            x = left;
        } else {
            x = right;
        }
        double xOld = x;
        addAction(x, xOld, function, functionDer1, actions);
        while (Math.abs(function.apply(x)) > precision) {
            x = xOld - function.apply(xOld) / functionDer1.apply(xOld);
            addAction(x, xOld, function, functionDer1, actions);
            xOld = x;
        }

        return new AbstractMap.SimpleEntry<>(x, actions);
    }

    private void addAction(double x, double xOld, Function<Double, Double> function, Function<Double, Double> functionDer1, List<Map<String, Double>> actions) {
        Map<String, Double> action = new HashMap<>();
        action.putIfAbsent("x_k", xOld);
        action.putIfAbsent("f(x_k)", function.apply(xOld));
        action.putIfAbsent("f'(x_k)", functionDer1.apply(xOld));
        action.putIfAbsent("x_k+1", x);
        action.putIfAbsent("|x_k+1 - x_k|", Math.abs(x - xOld));
        actions.add(action);
    }
}
