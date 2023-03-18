package org.nutsalhan87.web4backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ShotHandler {
    public static boolean isInRectangle(float x, float y, float r) {
        if (x > 0 || y < 0) {
            return false;
        }
        return y <= r && x >= -0.5 * r;
    }

    public static boolean isInTriangle(float x, float y, float r) {
        if (x > 0 || y > 0) {
            return false;
        }
        return y >= -x - r;
    }

    public static boolean isInQuartersphere(float x, float y, float r) {
        if (x < 0 || y < 0) {
            return false;
        }
        return (x * x + y * y) <= (r * r) / 4;
    }

    public static void validateOrThrow(float x, float y, float r) {
        if (x < -5 || x > 5 || y < -5 || y > 3 || r < 0 || r > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Укажите значения чисел в необходимых пределах");
        }
    }
}
