package ru.nutsalhan87.swt.math;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Cos implements Function {
    private Sin sin = new Sin();
    @Override
    public Double apply(Double x) {
        if (x == 0.) {
            return 1.;
        }

        return sin.apply(x + Constants.PI / 2);
    }

    @Override
    public String getName() {
        return "cos";
    }
}
