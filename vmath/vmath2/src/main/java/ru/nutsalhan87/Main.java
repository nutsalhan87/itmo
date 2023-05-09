package ru.nutsalhan87;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import ru.nutsalhan87.solve.*;
import ru.nutsalhan87.system.SystemNewton;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws PythonExecutionException, IOException {
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       System.out.println("Выберите подпрограмму:\n");
       switch (reader.readLine()) {
           case ("1") -> {
               nonSystem();
           }
           case ("2") -> {
               system();
           }
           default -> {
               System.out.println("Некорректный ввод");
           }
       }
    }

    public static void nonSystem() throws IOException, PythonExecutionException {
        System.out.println("""
                Выберите одно из уравнений:
                1. x^2 + 3x - 5
                2. 2sin(x) + e^x
                3. 0.5x^4 - x^3 + 2x""");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Function<Double, Double> function;
        switch (reader.readLine()) {
            case ("1") -> {
                function = arg -> Math.pow(arg, 2) + 3 * arg - 5;
            }
            case ("2") -> {
                function = arg -> 2 * Math.sin(arg) + Math.exp(arg);
            }
            case ("3") -> {
                function = arg -> 0.5 * Math.pow(arg, 4) - Math.pow(arg, 3) + 2 * arg;
            }
            default -> {
                System.out.println("Введено некорректное значение");
                return;
            }
        }

        Thread plot = new Thread(() -> {
            List<Double> x = NumpyUtils.linspace(-10, 10, 100);
            List<Double> y = x.stream().map(function).toList();
            Plot plt = Plot.create();
            plt.ylim(-10, 10);
            plt.plot().add(x, y);
            try {
                plt.show();
            } catch (IOException | PythonExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        plot.start();

        System.out.println("Ввод данных должен производиться через новую строку." +
                "Должны быть введены границы интервала и приближение");
        System.out.println("""
                Выберите метод ввода данных
                1. С клавиатуры
                2. С файла""");
        BufferedReader dataReader;
        switch (reader.readLine()) {
            case ("1") -> {
                dataReader = new BufferedReader(new InputStreamReader(System.in));
            }
            case ("2") -> {
                System.out.println("Введите путь к файлу:");
                dataReader = new BufferedReader(new FileReader(reader.readLine()));
            }
            default -> {
                System.out.println("Введено некорректное значение");
                return;
            }
        }
        double left = Double.parseDouble(dataReader.readLine());
        double right = Double.parseDouble(dataReader.readLine());
        double precision = Double.parseDouble(dataReader.readLine());

        System.out.println("""
                Выберите метод
                1. Метод половинного деления
                2. Метод хорд
                3. Метод Ньютона
                4. Метод секущих
                5. Метод простой итерации
                """);
        Solve solver;
        switch (reader.readLine()) {
            case ("1") -> {
                solver = new HalfDivide();
            }
            case ("2") -> {
                solver = new Chord();
            }
            case ("3") -> {
                solver = new Newton();
            }
            case ("4") -> {
                solver = new Secant();
            }
            case ("5") -> {
                solver = new SimpleIteration();
            }
            default -> {
                System.out.println("Введено некорректное значение");
                return;
            }
        }
        AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> answer = solver.solve(left, right, precision, function);
        System.out.println("""
                Выберите метод вывода данных
                1. На экран
                2. В файл""");
        BufferedWriter dataWriter;
        switch (reader.readLine()) {
            case ("1") -> {
                dataWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            }
            case ("2") -> {
                System.out.println("Введите путь к файлу:");
                dataWriter = new BufferedWriter(new FileWriter(reader.readLine()));
            }
            default -> {
                System.out.println("Введено некорректное значение");
                return;
            }
        }
        dataWriter.write("Ответ: " + answer.getKey().toString());
        dataWriter.newLine();
        writeTable(answer.getValue(), dataWriter);
    }

    public static void system() throws IOException, PythonExecutionException {
        System.out.println("""
                Выберите одну из систем:
                1. x^2 + y^2 = 4
                   y^2 = 3x^2
                2. x^3 + sin(y) = 0
                   2x + y^2 = 4
                3. tg(y) + cos(x) = 1
                   x^4 + y = 1""");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BiFunction<Double, Double, Double> functionF;
        BiFunction<Double, Double, Double> functionS;
        List<Double> xs = NumpyUtils.linspace(-10, 10, 100);
        List<Double> y1;
        List<Double> y2;
        List<Double> xRange = NumpyUtils.arange(-10, 10, 0.025);
        List<Double> yRange = NumpyUtils.arange(-10, 10, 0.025);
        NumpyUtils.Grid<Double> grid = NumpyUtils.meshgrid(xRange, yRange);
        Plot plt = Plot.create();
        plt.ylim(-10, 10);
        switch (reader.readLine()) {
            case ("1") -> {
                functionF = (x, y) -> Math.pow(x, 2) + Math.pow(y, 2) - 4;
                functionS = (x, y) -> y - 3 * Math.pow(x, 2);
            }
            case ("2") -> {
                functionF = (x, y) -> Math.pow(x, 3) + Math.sin(y);
                functionS = (x, y) -> 2 * x + Math.pow(y, 2) - 4;
            }
            case ("3") -> {
                functionF = (x, y) -> Math.tan(y) + Math.cos(x) - 1;
                functionS = (x, y) -> Math.pow(x, 4) + y - 1;
            }
            default -> {
                System.out.println("Введено некорректное значение");
                return;
            }
        }
        Thread thread = new Thread(() -> {
            List<List<Double>> g1 = grid.calcZ(functionF::apply);
            List<List<Double>> g2 = grid.calcZ(functionS::apply);
            plt.contour().add(xRange, yRange, g1).add(List.of(0));
            plt.contour().add(xRange, yRange, g2).add(List.of(0));
            try {
                plt.show();
            } catch (IOException | PythonExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        System.out.println("Введите через начальное приближение через новую строку");
        double x0 = Double.parseDouble(reader.readLine());
        double y0 = Double.parseDouble(reader.readLine());
        Map<String, Double> answer = new SystemNewton().solve(x0, y0, 0.01, functionF, functionS);
        System.out.println("Ответ: (" + answer.get("x") + "; " + answer.get("y") + ")");
        System.out.println("Количество итераций, за которое был найден ответ: " + answer.get("iters"));
        System.out.println("Вектор погрешностей: (" + answer.get("dx") + "; " + answer.get("dx") + ")");
    }

    public static void first() {
        Function<Double, Double> function = arg -> 2 * Math.pow(arg, 3) - 1.89 * Math.pow(arg, 2) - 5 * arg + 2.34;

        try {
            AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve = new SimpleIteration().solve(-2, -1, 0.01, function);
            System.out.println("Answer: " + solve.getKey());
            printTable(solve.getValue());
            System.out.println();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage() + "\n");
        }

        try {
            AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve = new HalfDivide().solve(0, 1, 0.01, function);
            System.out.println("Answer: " + solve.getKey());
            printTable(solve.getValue());
            System.out.println();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage() + "\n");
        }

        try {
            AbstractMap.SimpleEntry<Double, List<Map<String, Double>>> solve = new Secant().solve(1.5, 2, 0.01, function);
            System.out.println("Answer: " + solve.getKey());
            printTable(solve.getValue());
            System.out.println();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage() + "\n");
        }
    }
    
    public static void printTable(List<Map<String, Double>> actions) {
        if (actions.size() == 0) {
            return;
        }
        Set<String> keys = actions.get(0).keySet();
        keys.forEach(key -> System.out.print(key + " "));
        System.out.print("\n");
        actions.forEach(action -> {
            keys.forEach(key -> System.out.print(action.get(key) + " "));
            System.out.print("\n");
        });
    }

    public static void writeTable(List<Map<String, Double>> actions, Writer writer) throws IOException {
        if (actions.size() == 0) {
            return;
        }
        Set<String> keys = actions.get(0).keySet();
        keys.forEach(key -> {
            try {
                writer.write(key + " ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.write("\n");
        actions.forEach(action -> {
            keys.forEach(key -> {
                try {
                    writer.write(action.get(key) + " ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                writer.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.flush();
    }
}