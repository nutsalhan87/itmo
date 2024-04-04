package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.function.Function;
import java.util.stream.IntStream;

@NoArgsConstructor
class Ln implements Function<Double, Double> {
    private static final Pow powF = new Pow();
    private static final Double ln10 = 2.3025850929940456840179914546843642076011014886287729760333279009;

    @Override
    public Double apply(Double x) {
        if (x.isNaN() || x < 0) {
            return Double.NaN;
        } else if (x.isInfinite()) {
            return Double.POSITIVE_INFINITY;
        } else if (x == 0.) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == 1.) {
            return 0.;
        }

        var repr = new DoubleRepresentation(x);
        return lnSeriesAtZero(repr.mantissa) + repr.exponent * ln10;
    }

    private Double lnSeriesAtZero(Double x) {
        return -IntStream.range(1, 40 + 1).mapToDouble(k -> {
            int sign = k % 2 == 0 ? 1 : -1;
            return sign * powF.apply(x - 1, k) / k;
        }).sum();
    }

    @ToString
    private static class DoubleRepresentation {
        private final boolean isPositive;
        private final int exponent;
        private final double mantissa;

        public DoubleRepresentation(Double x) {
            this.isPositive = x >= 0.;
            if (!this.isPositive) {
                x *= -1;
            }

            int exponent = 0;
            while (x >= 1.) {
                x /= 10;
                exponent++;
            }
            while (x < 0.1 && x != 0.) {
                x *= 10;
                exponent--;
            }

            this.exponent = exponent;
            this.mantissa = x;
        }

        public double reconstruct() {
            int sign = isPositive ? 1 : -1;
            return sign * mantissa * powF.apply(10., exponent);
        }
    }
}
