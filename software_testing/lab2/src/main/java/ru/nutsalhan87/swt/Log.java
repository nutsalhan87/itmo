package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.BiFunction;

@NoArgsConstructor
class Log implements BiFunction<Double, Double, Double> {
    private static final Ln lnF = new Ln();

    @Override
    public Double apply(Double x, Double n) {
        // TODO: проверить на особые случаи и вернуть соответствующие занчения
        return lnF.apply(x) / lnF.apply(n);
    }
}
