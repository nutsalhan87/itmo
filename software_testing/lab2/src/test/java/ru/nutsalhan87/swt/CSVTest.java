package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Test;
import ru.nutsalhan87.swt.math.Sin;
import ru.nutsalhan87.swt.util.CSV;
import ru.nutsalhan87.swt.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CSVTest {
    @Test
    void test() {
        var sin = new Sin();
        var csv = new CSV<>(sin);
        List<Pair<Double, Double>> xy = new ArrayList<>();
        for (double x = -50.; x <= 50.; x += 0.1) {
            xy.add(new Pair<>(x, sin.apply(x)));
            csv.saveComputed(x);
        }
        var out = csv.format();
        var outputted = out.lines().skip(1).map(line -> {
            var doubles = line.split(",");
            Double x = Double.parseDouble(doubles[0]), y = Double.parseDouble(doubles[1]);
            return new Pair<>(x, y);
        }).toList();
        assertArrayEquals(xy.toArray(new Pair[0]), outputted.toArray(new Pair[0]));
    }
}