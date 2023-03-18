package general.route;

import java.io.Serializable;
import java.util.Date;

/**
 * The Route class is designed to store route data and contains data such as name, coordinates, distance,
 * start and end locations, as well as a unique identifier and the date of creation of the object
 */

public class Route implements Comparable<Route>, Serializable {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private general.route.location.first.Location from; //Поле не может быть null
    private general.route.location.second.Location to; //Поле не может быть null
    private double distance; //Значение поля должно быть больше 1
    private final String owner;

    public Route(String name, Date date, Coordinates coordinates, general.route.location.first.Location from,
                 general.route.location.second.Location to, double distance, Integer id, String owner) throws IllegalArgumentException {
        if (name == null || coordinates == null || from == null || to == null || name.equals("") || distance <= 1d) {
            throw new IllegalArgumentException("Неверные данные для объекта Route");
        }

        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.creationDate = date;
        this.id = id;
        this.owner = owner;
    }

    public Route(String name, Coordinates coordinates, general.route.location.first.Location from,
                 general.route.location.second.Location to, double distance) throws IllegalArgumentException {
        if (name == null || coordinates == null || from == null || to == null || name.equals("") || distance <= 1d) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        creationDate = new java.util.Date();
        this.id = 0;
        owner = null;
    }

    public void updateValues(Route toCopyValuesFrom) {
        if (toCopyValuesFrom.name == null || toCopyValuesFrom.coordinates == null || toCopyValuesFrom.from == null ||
                toCopyValuesFrom.to == null || toCopyValuesFrom.name.equals("") || toCopyValuesFrom.distance <= 1d) {
            throw new IllegalArgumentException();
        }

        this.name = toCopyValuesFrom.name;
        this.coordinates = toCopyValuesFrom.coordinates;
        this.from = toCopyValuesFrom.from;
        this.to = toCopyValuesFrom.to;
        this.distance = toCopyValuesFrom.distance;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException();
        }

        this.coordinates = coordinates;
    }

    public void setFrom(general.route.location.first.Location from) {
        if (from == null) {
            throw new IllegalArgumentException();
        }

        this.from = from;
    }

    public void setTo(general.route.location.second.Location to) {
        if (to == null) {
            throw new IllegalArgumentException();
        }

        this.to = to;
    }

    public void setDistance(double distance) {
        if (distance <= 1.0) {
            throw new IllegalArgumentException();
        }

        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public general.route.location.first.Location getFrom() {
        return from;
    }

    public general.route.location.second.Location getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public String getOwner() {return owner;}

    @Override
    public int compareTo(Route anotherRoute) {
        if (this.distance > anotherRoute.distance) return 1;
        else if (this.distance < anotherRoute.distance) return -1;
        else {
            if (this.coordinates.compareTo(anotherRoute.coordinates) != 0)
                return this.coordinates.compareTo(anotherRoute.coordinates);
            else {
                if (this.from.compareTo(anotherRoute.from) != 0) return this.from.compareTo(anotherRoute.from);
                else {
                    if (this.to.compareTo(anotherRoute.to) != 0) return this.to.compareTo(anotherRoute.to);
                    else {
                        return this.name.compareTo(anotherRoute.name);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Route: " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", distance=" + distance + "\n" +
                "Route.coordinates: " + coordinates + "\n" +
                "Route.from: " + from + "\n" +
                "Route.to: " + to + "\n" +
                "Owner: " + owner;
    }

    public String toJSON(int deep) {
        String paragraph = " ";
        return new String(new char[deep]).replace("\0", paragraph) + "\"Route\":\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "{\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "\"id\": " + id + ",\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "\"name\": \"" + name + "\",\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + coordinates.toJSON(deep + 2) + ",\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "\"distance\": " + distance + ",\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + from.toJSON(deep + 2) + ",\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + to.toJSON(deep + 2) + "\n" +
                new String(new char[deep]).replace("\0", paragraph) + "}";
    }
}
