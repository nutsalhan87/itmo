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
        String rName = inputString(input, RouteFields.R_NAME);
        Double rDistance = inputNumber(input, RouteFields.R_DISTANCE);
        Double cX = inputNumber(input, RouteFields.C_X);
        Integer cY = inputNumber(input, RouteFields.C_Y).intValue();
        Double flX = inputNumber(input, RouteFields.FL_X);
        Long flY = inputNumber(input, RouteFields.FL_Y).longValue();
        Double flZ = inputNumber(input, RouteFields.FL_Z);
        String flName = inputString(input, RouteFields.FL_NAME);
        Integer slX = inputNumber(input, RouteFields.SL_X).intValue();
        int slY = inputNumber(input, RouteFields.SL_Y).intValue();
        float slZ = inputNumber(input, RouteFields.SL_Z).floatValue();

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
            } catch (IllegalArgumentException ignored) {}
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
            } catch (IllegalArgumentException ignored) {}
        }

        return ret;
    }
}