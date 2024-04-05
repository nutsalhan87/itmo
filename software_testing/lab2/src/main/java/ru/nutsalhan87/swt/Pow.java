package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.BiFunction;

@NoArgsConstructor
class Pow implements BiFunction<Double, Integer, Double> {

    @Override
    public Double apply(Double x, Integer n) {
        boolean isRev = n < 0;
        n *= isRev ? -1 : 1;
        double ans = 1;
        while (n > 0) {
            ans *= x;
            n--;
        }

        return isRev ? 1 / ans : ans;
    }
}
