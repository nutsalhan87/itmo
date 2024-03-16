package ru.nutsalhan87.swt;

import java.util.Arrays;

public class Sec {
    public static double sec(double x) {
        long group = Math.round(x / Math.PI);
        long modifier = group % 2 == 0 ? 1 : -1;
        x = Math.abs(group == 0 ? x : x - Math.PI * group);
        if (Double.compare(x, Math.PI / 2) == 0) {
            return Double.NaN;
        } else if (x < Math.PI / 4) {
            return secNearZero(x) * modifier;
        } else {
            return secNearPi2(x) * modifier;
        }
    }

    private static double secNearZero(double x) {
        return Util.enumerate(Arrays.stream(new int[][]{{1, 1}, {1, 2}, {5, 24}, {61, 720}, {277, 8_064}, {50_521, 3_628_800}}))
                .mapToDouble((v) -> Math.pow(x, v.getIndex() * 2) * v.getObject()[0] / v.getObject()[1])
                .sum();
    }

    private static double secNearPi2(double x) {
        double mod = x - Math.PI / 2;
        return -1 / mod - mod / 6 - 7 * Math.pow(mod, 3) / 360 - 31 * Math.pow(mod, 5) / 15_120;
    }
}