package ru.nutsalhan87.swt;

import org.mockito.Mockito;
import ru.nutsalhan87.swt.math.*;
import ru.nutsalhan87.swt.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class MockedFactory {
    public static Pair<Function, Set<Double>> mock(Function function) throws IOException {
        var map = readFile("src/test/resources/" + function.getName() + ".csv");
        Function mocked = Mockito.mock();
        when(mocked.apply(anyDouble())).thenAnswer(invocationOnMock -> {
            Double x = invocationOnMock.getArgument(0);
            Double y = map.get(x);
            if (y == null) {
                throw new IllegalArgumentException("x must be from table, but x = " + x);
            }
            return y;
        });
        return new Pair<>(mocked, map.keySet());
    }

    public static Pair<FunctionCompute, Set<Double>> mockedFunctionCompute() throws IOException {
        var pow3 = mock(new Pow(3));
        var tg = mock(new Tg());
        var cosec = mock(new Cosec());
        var sec = mock(new Sec());
        var ln = mock(new Ln());
        var log3 = mock(new Log(3));
        var log5 = mock(new Log(5));

        var commonValueSet = new HashSet<>(pow3.s());
        commonValueSet.retainAll(tg.s());
        commonValueSet.retainAll(cosec.s());
        commonValueSet.retainAll(sec.s());
        commonValueSet.retainAll(ln.s());
        commonValueSet.retainAll(log3.s());
        commonValueSet.retainAll(log5.s());

        var functionCompute = new FunctionCompute(
                pow3.f(),
                tg.f(),
                cosec.f(),
                sec.f(),
                ln.f(),
                log3.f(),
                log5.f()
        );

        return new Pair<>(functionCompute, commonValueSet);
    }

    private static TreeMap<Double, Double> readFile(String filename) throws IOException {
        var xys = new TreeMap<Double, Double>();
        String csv = Files.readString(Path.of(filename));
        csv.lines().forEach(line -> {
            var xy = Arrays.stream(line.split(",")).
                    map(String::strip).
                    mapToDouble(Double::parseDouble).
                    toArray();
            xys.put(xy[0], xy[1]);
        });
        return xys;
    }
}
