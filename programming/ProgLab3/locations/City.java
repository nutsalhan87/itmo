package locations;

import java.util.ArrayList;

public class City extends Location{
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
    public String toString() {
        return name;
    }
}
