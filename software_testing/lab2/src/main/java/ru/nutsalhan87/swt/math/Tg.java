package ru.nutsalhan87.swt.math;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Tg implements Function {
    private Sin sin = new Sin();
    private Cos cos = new Cos();

    @Override
    public Double apply(Double x) {
        if (x == 0.) {
            return x;
        } else if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        } else if (Math.abs(cos.apply(x)) < Constants.EPSILON) {
            return Double.POSITIVE_INFINITY;
        }

        return sin.apply(x) / cos.apply(x);
    }

    @Override
    public String getName() {
        return "tg";
    }
}
