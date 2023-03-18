package exceptions;

import locations.Location;

public class RocketDoesntWorkRuntimeException extends RuntimeException {
    public RocketDoesntWorkRuntimeException(Location loc1, Location loc2) {
        super("Ракета не работает и не может совершить перемещение с локации " + loc1.toString() + " на локацию " + loc2.toString());
    }
}
