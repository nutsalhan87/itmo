package ru.nutsalhan87.swt.math;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Sec implements Function {
    private Cos cos = new Cos();

    @Override
    public Double apply(Double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        } else if (Math.abs(cos.apply(x)) < Constants.EPSILON) {
            return Double.POSITIVE_INFINITY;
        }

        return 1 / cos.apply(x);
    }

    @Override
    public String getName() {
        return "sec";
    }
}
