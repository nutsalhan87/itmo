package ru.nutsalhan87.swt;

import org.mockito.Mockito;
import ru.nutsalhan87.swt.math.*;
import ru.nutsalhan87.swt.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                throw new IllegalArgumentException(function.getName() + " x must be from table, but x = " + x);
            }
            return y;
        });
        when(mocked.getName()).thenReturn("mocked " + function.getName());
        return new Pair<>(mocked, map.keySet());
    }

    public static Pair<FunctionCompute, Set<Double>> mockedLogFunctionCompute() throws IOException {
        var pow3 = new Pow(3);
        var tg = new Tg();
        var cosec = new Cosec();
        var sec = new Sec();
        var ln = mock(new Ln());
        var log3 = mock(new Log(3));
        var log5 = mock(new Log(5));

        var commonValueSet = new HashSet<>(ln.s());
        commonValueSet.retainAll(ln.s());
        commonValueSet.retainAll(log3.s());
        commonValueSet.retainAll(log5.s());

        var functionCompute = new FunctionCompute(
                pow3,
                tg,
                cosec,
                sec,
                ln.f(),
                log3.f(),
                log5.f(),
                "log mocked function"
        );

        return new Pair<>(functionCompute, commonValueSet);
    }

    public static Pair<FunctionCompute, Set<Double>> mockedTrigonometryFunctionCompute() throws IOException {
        var pow3 = new Pow(3);
        var tg = mock(new Tg());
        var cosec = mock(new Cosec());
        var sec = mock(new Sec());
        var ln = new Ln();
        var log3 = new Log(3);
        var log5 = new Log(5);

        var commonValueSet = new HashSet<>(tg.s());
        commonValueSet.retainAll(cosec.s());
        commonValueSet.retainAll(sec.s());

        var functionCompute = new FunctionCompute(
                pow3,
                tg.f(),
                cosec.f(),
                sec.f(),
                ln,
                log3,
                log5,
                "trigonometry mocked function"
        );

        return new Pair<>(functionCompute, commonValueSet);
    }

    public static List<Pair<FunctionCompute, Set<Double>>> mockedFunctionComputeCombination() throws IOException {
        var functions = Stream.of(new Object[][] {
                { "pow3", new Pow(3) },
                { "tg", new Tg() },
                { "cosec", new Cosec() },
                { "sec", new Sec() },
                { "ln", new Ln() },
                { "log3", new Log(3) },
                { "log5", new Log(5) },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Function) data[1]));

        List<Pair<FunctionCompute, Set<Double>>> mockedFunctionComputes = new ArrayList<>();
        for (String s : functions.keySet()) {
            if (s.equals("pow3")) {
                continue;
            }
            var mockedFunctions = new HashMap<>(functions);
            var functionAndValues = mock(functions.get(s));
            mockedFunctions.put(s, functionAndValues.f());
            var functionCompute = new FunctionCompute(
                    mockedFunctions.get("pow3"),
                    mockedFunctions.get("tg"),
                    mockedFunctions.get("cosec"),
                    mockedFunctions.get("sec"),
                    mockedFunctions.get("ln"),
                    mockedFunctions.get("log3"),
                    mockedFunctions.get("log5"),
                    s + " unmocked function"
            );
            var apply = new Pair<>(functionCompute, functionAndValues.s());
            mockedFunctionComputes.add(apply);
        }
        return mockedFunctionComputes;
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
