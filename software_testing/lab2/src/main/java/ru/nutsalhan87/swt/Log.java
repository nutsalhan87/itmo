package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.BiFunction;

@NoArgsConstructor
class Log implements BiFunction<Double, Double, Double> {
    private static final Ln lnF = new Ln();

    @Override
    public Double apply(Double x, Double n) {
        if(n==1){
            return Double.POSITIVE_INFINITY;
        }
        else if(n<=0){
            return  Double.NaN;
        }
        else if(x==0){
            return  Double.NEGATIVE_INFINITY;
        }
        else if(x<0){
            return Double.NaN;
        }

        return lnF.apply(x) / lnF.apply(n);
    }
}
