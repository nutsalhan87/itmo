package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
class Tan implements Function<Double, Double> {
    private static final Sin sinF = new Sin();
    private static final Cos cosF = new Cos();
    @Override
    public Double apply(Double x) {
        if (cosF.apply(x) == 0.||Double.isNaN(x)) {
            return Double.NaN;
        }

        return sinF.apply(x)/cosF.apply(x);
    }
}
