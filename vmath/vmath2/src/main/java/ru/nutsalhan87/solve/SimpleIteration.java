package ru.nutsalhan87.solve;

import ru.nutsalhan87.Util;

import java.util.*;
import java.util.function.Function;

public class SimpleIteration implements Solve {
    @Override
    public AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve(double left, double right, double precision, Function<Double, Double> function) {
        if (function.apply(left) * function.apply(right) > 0) {
            throw new IllegalArgumentException("На границах интервала одинаковые знаки");
        }

        List<Map<String, Double>> actions = new ArrayList<>();
        Function<Double, Double> functionDer1 = Util.getDerivative(function, 1);
        Function<Double, Double> functionDer2 = Util.getDerivative(function, 2);
        double l = -1 / Math.max(Math.abs(functionDer1.apply(left)), Math.abs(functionDer1.apply(right)));
        Function<Double, Double> phi = (arg) -> arg + l * function.apply(arg);
        Function<Double, Double> phiDer1 = Util.getDerivative(phi, 1);
        double q = Math.max(Math.abs(phiDer1.apply(left)), Math.abs(phiDer1.apply(right)));
        double x = function.apply(left) * functionDer2.apply(left) > 0 ? left : right;
        double x1 = phi.apply(x);
        addAction(x1, x, function, actions);
        while (Math.abs(x - x1) > precision) {
            if (Math.abs(phiDer1.apply(x1)) > q) {
                throw new IllegalArgumentException("Не сходится по достаточному условию сходимости");
            }
            x = x1;
            x1 = phi.apply(x);
            addAction(x1, x, function, actions);
        }

        return new AbstractMap.SimpleEntry<>(x1, actions);
    }

    private void addAction(double x, double xOld, Function<Double, Double> function, List<Map<String, Double>> actions) {
        Map<String, Double> action = new HashMap<>();
        action.putIfAbsent("x_k", xOld);
        action.putIfAbsent("x_k+1", x);
        action.putIfAbsent("f(x_k+1)", function.apply(x));
        action.putIfAbsent("|x_k+1 - x_k|", Math.abs(x - xOld));
        actions.add(action);
    }
}
