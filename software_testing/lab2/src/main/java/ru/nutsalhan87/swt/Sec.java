package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
class Sec implements Function<Double, Double> {
    private static final Sin cosF = new Sin();

    @Override
    public Double apply(Double x) {
        if (cosF.apply(x) == 0.||Double.isNaN(x)) {
            return Double.NaN;
        }

        return 1/cosF.apply(x);
    }
}
