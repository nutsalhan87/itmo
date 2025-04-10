package ru.nutsalhan87.swt.math;

public class Pow implements Function {
    private final int n;
    private final boolean isRev;

    public Pow(int n) {
        this.isRev = n < 0;
        this.n = n * (this.isRev ? -1 : 1);
    }

    @Override
    public Double apply(Double x) {
        double ans = 1;
        for (int i = 0; i < n; ++i) {
            ans *= x;
        }

        return isRev ? 1 / ans : ans;
    }

    @Override
    public String getName() {
        return "pow" + n;
    }
}
