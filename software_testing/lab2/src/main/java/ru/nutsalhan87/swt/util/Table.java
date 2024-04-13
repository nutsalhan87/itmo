package ru.nutsalhan87.swt.util;

import ru.nutsalhan87.swt.math.Function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.TreeMap;

public class Table implements Function {
    private final String name;
    private final TreeMap<Double, Double> values = new TreeMap<>();

    public Table(Function function) throws IOException {
        this.name = "table " + function.getName();

        String filename = "src/main/resources/" + function.getName() + ".csv";
        String csv = Files.readString(Path.of(filename));
        csv.lines().forEach(line -> {
            var xy = Arrays.stream(line.split(",")).
                    map(String::strip).
                    mapToDouble(Double::parseDouble).
                    toArray();
            this.values.put(xy[0], xy[1]);
        });
    }


    @Override
    public Double apply(Double x) {
        if (x < values.firstKey() || x > values.lastKey()) {
            return Double.NaN;
        }
        var floorValue = values.floorEntry(x).getValue();
        var ceilingValue = values.ceilingEntry(x).getValue();
        return (floorValue + ceilingValue) / 2.;
    }

    @Override
    public String getName() {
        return name;
    }
}
