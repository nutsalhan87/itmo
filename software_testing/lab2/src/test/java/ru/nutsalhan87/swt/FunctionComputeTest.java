package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nutsalhan87.swt.math.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Double.NaN;
import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionComputeTest {
    private final FunctionCompute functionCompute;
    private final FunctionCompute mockedFunctionCompute;
    private final Set<Double> tableValues;

    public FunctionComputeTest() throws IOException {
        this.functionCompute = new FunctionCompute(
                new Pow(3),
                new Tg(),
                new Cosec(),
                new Sec(),
                new Ln(),
                new Log(3),
                new Log(5)
        );
        var p = MockedFactory.mockedFunctionCompute();
        this.mockedFunctionCompute = p.f();
        this.tableValues = p.s();
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
    void other() {
        List<Double> expected = new ArrayList<>();
        List<Double> actual = new ArrayList<>();
        for (double x : tableValues) {
            expected.add(mockedFunctionCompute.apply(x));
            actual.add(functionCompute.apply(x));
        }
        assertArrayEquals(expected.stream().mapToDouble(i -> i).toArray(),
                actual.stream().mapToDouble(i -> i).toArray(),
                Constants.EPSILON);
    }
}
