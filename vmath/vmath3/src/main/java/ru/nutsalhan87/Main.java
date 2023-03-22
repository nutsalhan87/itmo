package ru.nutsalhan87;

import ru.nutsalhan87.solve.Integrate;
import ru.nutsalhan87.solve.Rectangle;
import ru.nutsalhan87.solve.Simpson;
import ru.nutsalhan87.solve.Trapezoid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws IOException {
        Function<Double, Double> function;
        double left;
        double right;
        double precision;
        int steps = 4;
        Integrate integrate;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("""
                Выберите функцию для интегрирования:
                1. x^3 - 3x^2 + 7x - 10
                2. 2x^3 - 5x^2 - 3x + 21
                3. 7x^3 - 2x^2 - 9x + 36
                """);
        switch (reader.readLine()) {
            case ("1") -> {
                function = (arg) -> Math.pow(arg, 3) - 3 * Math.pow(arg, 2) + 7 * arg - 10;
            }
            case ("2") -> {
                function = (arg) -> 2 * Math.pow(arg, 3) - 5 * Math.pow(arg, 2) - 3 * arg + 21;
            }
            case ("3") -> {
                function = (arg) -> 7 * Math.pow(arg, 3) - 2 * Math.pow(arg, 2) - 9 * arg + 36;
            }
            default -> {
                incorrect();
                return;
            }
        }

        System.out.println("Введите пределы интегрирования (сначала левый, а потом правый):");
        left = Double.parseDouble(reader.readLine());
        right = Double.parseDouble(reader.readLine());

        System.out.println("Введите точность:");
        precision = Double.parseDouble(reader.readLine());

        System.out.println("""
                Выберите метод нахождения интеграла:
                1. Метод прямоугольника - левый
                2. Метод прямоугольника - центр
                3. Метод прямоугольника - правый
                4. Метод трапеций
                5. Метод Симпсона
                """);
        switch (reader.readLine()) {
            case ("1") -> {
                integrate = Rectangle.left();
            }
            case ("2") -> {
                integrate = Rectangle.center();
            }
            case ("3") -> {
                integrate = Rectangle.right();
            }
            case ("4") -> {
                integrate = Trapezoid.trapezoid();
            }
            case ("5") -> {
                integrate = Simpson.simpson();
            }
            default -> {
                incorrect();
                return;
            }
        }

        while (Math.abs(integrate.apply(function, left, right, steps) - integrate.apply(function, left, right, steps * 2)) >= precision) {
            steps *= 2;
        }
        steps *= 2;
        System.out.println("Ответ: " + integrate.apply(function, left, right, steps));
        System.out.println("Количество шагов: " + steps);
    }

    public static void incorrect() {
        System.out.println("Введено некорректное значение");
    }
}