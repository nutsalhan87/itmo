package ru.nutsalhan87.swt;

import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
class Sin implements Function<Double, Double> {
    private static final Pow powF = new Pow();
    private static final Double oneDivSqrtTwo = 0.7071067811865475244008443621048490392848359376884740365883398689;

    @Override
    public Double apply(Double x) {
        if (x.isNaN() || x.isInfinite()) {
            return Double.NaN;
        } else if (x == 0.) {
            return x;
        }

        int sign = x >= 0 ? 1 : -1;
        x *= sign;
        int index = (int)(2 * x / Constants.PI);
        int group = index % 4;
        x -= index * Constants.PI / 2;
        return sign * switch (group) {
            case 0 -> sinPart(x);
            case 1 -> sinPart(Constants.PI / 2 - x);
            case 2 -> -sinPart(x);
            case 3 -> -sinPart(Constants.PI / 2 - x);
            default -> throw new IllegalStateException("Unexpected value: " + group);
        };
    }

    private Double sinPart(Double x) throws IllegalArgumentException {
        if (x < 0 || x > Constants.PI / 2) {
            throw new IllegalArgumentException("Internal intermediate value should be in [0; pi/2], but it is " + x);
        }
        int divider = 1;
        double ans = 0;
        double argShift = x - (Constants.PI / 4);
        for (int power = 0; power < 11; ++power) {
            if (power != 0) {
                divider *= power;
            }
            if ((power / 2) % 2 == 0) {
                ans += oneDivSqrtTwo * powF.apply(argShift, power) / divider;
            } else {
                ans += -oneDivSqrtTwo * powF.apply(argShift, power) / divider;
            }
        }

        return ans;
    }


}
