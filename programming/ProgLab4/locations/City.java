package locations;

import java.util.ArrayList;
import java.util.Objects;

public class City extends Location {
    private ArrayList<Market> markets;
    private ArrayList<RailStation> railStations;

    public City(String inputName) {
        super(inputName);
        markets = new ArrayList<Market>();
        railStations = new ArrayList<RailStation>();
    }

    public void setMarket(Market loc) {
        markets.add(loc);
    }

    public void setRailStation(RailStation loc) {
        railStations.add(loc);
    }

    public ArrayList<Market> getMarkets() {
        return markets;
    }

    public ArrayList<RailStation> getRailStations() {
        return railStations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        City city = (City) o;
        return markets.equals(city.markets) && railStations.equals(city.railStations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), markets, railStations);
    }
}
