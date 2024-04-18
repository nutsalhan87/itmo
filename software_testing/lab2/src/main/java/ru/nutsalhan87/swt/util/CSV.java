package ru.nutsalhan87.swt.util;

import ru.nutsalhan87.swt.math.Function;

import java.util.ArrayList;
import java.util.List;

public class CSV<T extends Function> {
    private final T f;
    private final List<Pair<Double, Double>> results;

    public CSV(T function) {
        this.f = function;
        this.results = new ArrayList<>();
    }

    public void saveComputed(double... x) {
        for (double v : x) {
            results.add(new Pair<>(v, f.apply(v)));
        }
    }

    public void print() {
        System.out.printf("x, %s(x)%n", f.getName());
        results.forEach(x -> System.out.printf("%s, %s%n", x.f(), x.s()));
    }
}
