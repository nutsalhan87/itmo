package server.workwithexternaldata;

import general.route.Coordinates;
import general.route.Route;
import server.workwithexternaldata.parsedobjects.ParsedObject;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class implements a single method that converts an object of the special
 * class ParsedObject to the list of Route objects
 */

public class ParsedObjectToListRoute {
    private ParsedObjectToListRoute() {
    }

    public static List<Route> convertToListRoute(ParsedObject parsedObject) {
        List<Route> data = new LinkedList<>();

        for (int i = 0; i < parsedObject.asList().size(); ++i) {
            try {
                data.add(extractRouteObject(parsedObject, i));
            }
            catch (NullPointerException exc) {
                System.out.println("Объект под номером " + (i + 1) + " не может быть добавлен вследствие некорректных данных.");
            }
            catch (IllegalArgumentException exci) {
                System.out.println(exci.getMessage());
            }
        }
        data.sort(Comparator.comparing(Route::getId));
        return data;
    }

    private static Route extractRouteObject(ParsedObject parsedObject, int i) {
        Coordinates coordinates = new Coordinates(
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("coordinates").asMap().get("x").asNumber(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("coordinates").asMap().get("y").asNumber().intValue()
        );

        general.route.location.first.Location from = new general.route.location.first.Location(
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("x").asNumber(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("y").asNumber().longValue(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("z").asNumber(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("name").asString()
        );

        general.route.location.second.Location to = new general.route.location.second.Location(
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("second_location").asMap().get("Location").asMap().get("x").asNumber().intValue(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("second_location").asMap().get("Location").asMap().get("y").asNumber().intValue(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("second_location").asMap().get("Location").asMap().get("z").asNumber().floatValue()
        );

        Integer id = parsedObject.asList().get(i).asMap().get("Route").asMap().get("id").asNumber().intValue();

        return new Route(
                parsedObject.asList().get(i).asMap().get("Route").asMap().get("name").asString(),
                coordinates,
                from,
                to,
                parsedObject.asList().get(i).asMap().get("Route").asMap().get("distance").asNumber(),
                id
        );
    }
}
