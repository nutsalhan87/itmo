package client.workwithroute;

import client.Input;
import general.route.Coordinates;
import general.route.Route;

import java.io.IOException;

/**
 * The class contains a single method that creates an instance of the Route class by entering each data field manually or from a file
 */

public class CreatingNewInstance {
    private CreatingNewInstance() {
    }

    public static Route createNewRouteInstance(Input input) throws IOException {

        System.out.println("Условимся, что пустая строка - это null");

        System.out.println("Задание основных значений полей для объекта класса Route:");
        System.out.print("Введите имя: ");
        String rName = inputString(input, RouteFields.R_NAME);
        System.out.print("Введите дистанцию: ");
        Double rDistance = inputNumber(input, RouteFields.R_DISTANCE);

        System.out.println("Задание основных значений полей для объекта класса Coordinates:");
        System.out.print("Введите значение x: ");
        Double cX = inputNumber(input, RouteFields.C_X);
        System.out.print("Введите значение y: ");
        Integer cY = inputNumber(input, RouteFields.C_Y).intValue();

        System.out.println("Задание основных значений полей для объекта класса route.location.first.Location:");
        System.out.print("Введите значение x: ");
        Double flX = inputNumber(input, RouteFields.FL_X);
        System.out.print("Введите значение y: ");
        Long flY = inputNumber(input, RouteFields.FL_Y).longValue();
        System.out.print("Введите значение z: ");
        Double flZ = inputNumber(input, RouteFields.FL_Z);
        System.out.print("Введите имя: ");
        String flName = inputString(input, RouteFields.FL_NAME);

        System.out.println("Задание основных значений полей для объекта класса route.location.second.Location:");
        System.out.print("Введите значение x: ");
        Integer slX = inputNumber(input, RouteFields.SL_X).intValue();
        System.out.print("Введите значение y: ");
        Integer slY = inputNumber(input, RouteFields.SL_Y).intValue();
        System.out.print("Введите значение z: ");
        Float slZ = inputNumber(input, RouteFields.SL_Z).floatValue();

        return new Route(rName, new Coordinates(cX, cY), new general.route.location.first.Location(flX, flY, flZ, flName),
                new general.route.location.second.Location(slX, slY, slZ), rDistance);
    }

    private static Double inputNumber(Input cin, RouteFields field) throws IOException {
        boolean continueKey = false;
        String input;
        Double ret = null;
        while (!continueKey) {
            input = cin.readLine();
            try {
                input = ValuesChecking.checkCondition(input, field);
                ret = Double.parseDouble(input);
                continueKey = true;
            } catch (NumberFormatException exn) {
                System.out.println("Введите корректное число");
            } catch (IllegalArgumentException excar) {
                System.out.println(excar.getMessage());
            }
        }

        return ret;
    }

    private static String inputString(Input cin, RouteFields field) throws IOException {
        boolean continueKey = false;
        String input;
        String ret = null;
        while (!continueKey) {
            input = cin.readLine();
            try {
                input = ValuesChecking.checkCondition(input, field);
                ret = input;
                continueKey = true;
            } catch (IllegalArgumentException excar) {
                System.out.println(excar.getMessage());
            }
        }

        return ret;
    }
}