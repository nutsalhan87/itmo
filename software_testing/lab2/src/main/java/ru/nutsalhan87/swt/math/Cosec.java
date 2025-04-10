package ru.nutsalhan87.swt.math;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Cosec implements Function {
    private Sin sin = new Sin();

    @Override
    public Double apply(Double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        } else if (Math.abs(sin.apply(x)) < Constants.EPSILON) {
            return Double.POSITIVE_INFINITY;
        }

        return 1 / sin.apply(x);
    }

    @Override
    public String getName() {
        return "cosec";
    }
}