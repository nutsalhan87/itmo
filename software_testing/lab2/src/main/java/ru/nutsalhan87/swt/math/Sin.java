package ru.nutsalhan87.swt.math;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.nutsalhan87.swt.util.PowTriplet;

import java.util.List;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
public class Sin implements Function {
    private List<PowTriplet> pows = IntStream.range(0, 11).
            mapToObj(i -> new PowTriplet(i, (i / 2) % 2 == 0 ? 1 : -1, new Pow(i))).
            toList();
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
        for (var pair : pows) {
            if (pair.power() != 0) {
                divider *= pair.power();
            }
            ans += oneDivSqrtTwo * pair.sign() * pair.pow().apply(argShift) / divider;
        }

        return ans;
    }

    @Override
    public String getName() {
        return "sin";
    }
}
