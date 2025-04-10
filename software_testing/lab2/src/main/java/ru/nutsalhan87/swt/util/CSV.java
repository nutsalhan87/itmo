package ru.nutsalhan87.swt.util;

import ru.nutsalhan87.swt.math.Function;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV<T extends Function> {
    private final T f;
    private final List<Pair<Double, Double>> results;

    public CSV(T function) {
        this.f = function;
        this.results = new ArrayList<>();
    }

    public void saveComputed(double... x) {
        for (double v : x) {
            results.add(new Pair<>(v, f.apply(v)));
        }
    }

    public void saveToFile() throws IOException {
        var csvFile = new File("out_csv/" + f.getName() + ".csv");
        csvFile.delete();
        csvFile.createNewFile();
        try (var csvWriter = new FileWriter(csvFile)) {
            csvWriter.write(format());
        }
    }

    public String format() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("x, %s(x)%n", f.getName()));
        results.forEach(x -> stringBuilder.append(String.format("%s, %s%n", x.f(), x.s())));
        return stringBuilder.toString();
    }
}
