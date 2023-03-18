package consoleapplication.workwithroute;

import route.Coordinates;
import route.Route;
import route.location.second.Location;
import workwithexternaldata.parsedobjects.ParsedObject;

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
                System.out.println("Объект под номером " + (i + 1) + " не может быть добавлен вследствие некорреткных данных.");
            }
            catch (IllegalArgumentException exci) {
                System.out.println(exci.getMessage());
            }
        }

        return data;
    }

    private static Route extractRouteObject(ParsedObject parsedObject, int i) {
        Coordinates coordinates = new Coordinates(
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("coordinates").asMap().get("x").asNumber(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("coordinates").asMap().get("y").asNumber().intValue()
        );

        route.location.first.Location from = new route.location.first.Location(
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("x").asNumber(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("y").asNumber().longValue(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("z").asNumber(),
                parsedObject.asList().get(i).asMap().get("Route").
                        asMap().get("first_location").asMap().get("Location").asMap().get("name").asString()
        );

        Location to = new Location(
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
