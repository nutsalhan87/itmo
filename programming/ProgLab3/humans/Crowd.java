package humans;

import locations.Location;

public class Crowd extends Humans{
    public Crowd(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Толпа";
    }
}
