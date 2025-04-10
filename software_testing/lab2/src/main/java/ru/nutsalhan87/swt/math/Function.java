package ru.nutsalhan87.swt.math;

public interface Function extends java.util.function.Function<Double, Double> {
    default String getName() {
        return "f";
    }
}
