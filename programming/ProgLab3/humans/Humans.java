package humans;

import locations.Location;

import java.util.Objects;

public abstract class Humans {
    protected Location location;

    public Humans(Location inputLocation) {
        location = inputLocation;
    }

    public void changeLocation(Location newLocation) {
        System.out.println(this + " изменил свое местоположение с локации " + location + " на локацию " + newLocation);
        location = newLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Humans humans = (Humans) o;
        return Objects.equals(location, humans.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "Человеческая сущность или множество таких сущеностей";
    }
}
