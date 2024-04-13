package ru.nutsalhan87.swt.math;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.nutsalhan87.swt.util.PowTriplet;

import java.util.List;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
public class Ln implements Function {
    private List<PowTriplet> pows = IntStream.range(1, 65 + 1).
            mapToObj(i -> new PowTriplet(i, i % 2 == 0 ? 1 : -1, new Pow(i))).
            toList();
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
        return -pows.stream().
                mapToDouble(triplet -> triplet.sign() * triplet.pow().apply(x - 1) / triplet.power()).
                sum();
    }

    @Override
    public String getName() {
        return "ln";
    }

    private static class DoubleRepresentation {
        private final boolean isPositive;
        private final int exponent;
        private final double mantissa;
        private final Pow pow;

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
            this.pow = new Pow(exponent);
        }

        public double reconstruct() {
            int sign = isPositive ? 1 : -1;
            return sign * mantissa * pow.apply(10.);
        }
    }
}
