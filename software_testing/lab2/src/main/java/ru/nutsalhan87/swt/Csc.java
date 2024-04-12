package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
class Csc implements Function<Double, Double> {
    private static final Sin sinF = new Sin();

    @Override
    public Double apply(Double x) {
        if (sinF.apply(x) == 0.||Double.isNaN(x)) {
            return Double.NaN;
        }

        return 1/sinF.apply(x);
    }
}
