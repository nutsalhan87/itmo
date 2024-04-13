package ru.nutsalhan87.swt.math;

import java.text.DecimalFormat;

public class Log implements Function {
    private static final DecimalFormat decimalFormat = new DecimalFormat();
    static {
        decimalFormat.setMaximumFractionDigits(3);
    }

    private Ln ln = new Ln();
    private final double n;

    public Log(double n) {
        this.n = n;
    }

    public Log(double n, Ln ln) {
        this.n = n;
        this.ln = ln;
    }

    @Override
    public Double apply(Double x) {
        if (x == 0.) {
            return Double.NEGATIVE_INFINITY;
        } else if (x < 0. || n <= 0.) {
            return Double.NaN;
        } else if (n == 1) {
            return Double.POSITIVE_INFINITY;
        }

        return ln.apply(x) / ln.apply(n);
    }

    @Override
    public String getName() {
        return "log" + decimalFormat.format(n);
    }
}
