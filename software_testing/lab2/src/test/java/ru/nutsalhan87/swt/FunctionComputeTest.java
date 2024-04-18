package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nutsalhan87.swt.math.*;

import java.io.IOException;

import static java.lang.Double.NaN;
import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionComputeTest {
    private final FunctionCompute functionCompute;

    public FunctionComputeTest() {
        this.functionCompute = new FunctionCompute(
                new Pow(3),
                new Tg(),
                new Cosec(),
                new Sec(),
                new Ln(),
                new Log(3),
                new Log(5),
                "function"
        );
    }

    @Test
    void nanResult() {
        // вообще, здесь должен быть разрыв "полюс", то есть +inf,
        // но из-за решения "шаг за шагом" без мат. преобразований выходит NaN
        assertEquals(NaN, functionCompute.apply(1.));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0., -0.5 * PI, -PI, -1.5 * PI, -2 * PI, -2.5 * PI, -3 * PI}) // n * pi/2 where n is integer
    void infResult(double x) {
        assertTrue(functionCompute.apply(x).isInfinite());
    }

    @Test
    void mockedLog() throws IOException {
        var mocked = MockedFactory.mockedLogFunctionCompute();
        for (double x : mocked.s()) {
            var expected = restrict(mocked.f().apply(x));
            var actual = restrict(functionCompute.apply(x));
            assertEquals(expected, actual, 1, "x = " + x);
        }
    }

    @Test
    void mockedTrigonometry() throws IOException {
        var mocked = MockedFactory.mockedTrigonometryFunctionCompute();
        for (double x : mocked.s()) {
            var expected = restrict(mocked.f().apply(x));
            var actual = restrict(functionCompute.apply(x));
            assertEquals(expected, actual, 1, "x = " + x);
        }
    }

    @Test
    void singleUnmocked() throws IOException {
        for (var partiallyMockedFunctionCompute : MockedFactory.mockedFunctionComputeCombination()) {
            for (double x : partiallyMockedFunctionCompute.s()) {
                var expected = restrict(partiallyMockedFunctionCompute.f().apply(x));
                var actual = restrict(functionCompute.apply(x));
                assertEquals(expected, actual, 1, partiallyMockedFunctionCompute.f().getName() + ": x = " + x);
            }
        }
    }

    double restrict(Double x) {
        if (x.isInfinite() || x > 1e9) {
            return NaN;
        }
        return x;
    }
}
