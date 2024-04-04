package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
class Cos implements Function<Double, Double> {
    private static final Sin sinF = new Sin();
    @Override
    public Double apply(Double x) {
        if (x == 0.) {
            return 1.;
        }

        return sinF.apply(x + Constants.PI / 2);
    }
}
