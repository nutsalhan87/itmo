package org.nutsalhan87.web3;

import java.time.Instant;
import java.util.Date;

public class HitHandler {
    public static Shot process(float x, float y, float r) {
        Instant time = Instant.now();
        String date = Clock.formatter.format(new Date());
        boolean result = isInRectangle(x, y, r) || isInTriangle(x, y, r) || isInQuartersphere(x, y, r);
        return new Shot(date, (Instant.now().getNano() - time.getNano()) / 1000, x, y, r, result);
    }

    private static boolean isInRectangle(float x, float y, float r) {
        if (x > 0 || y < 0) {
            return false;
        }
        return y <= r && x >= -0.5 * r;
    }

    private static boolean isInTriangle(float x, float y, float r) {
        if (x > 0 || y > 0) {
            return false;
        }
        return y >= (-x - r / 2);
    }

    private static boolean isInQuartersphere(float x, float y, float r) {
        if (x < 0 || y > 0) {
            return false;
        }
        return (x * x + y * y) <= (r * r);
    }
}
