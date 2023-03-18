package locations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import stocks.*;
import java.util.Random;

public class Cities {
    public static City BRAHENVILLE;
    public static City DAVILON;
    public static City GRABENBERG;
    public static City SAN_KOMARIKA;

    static {
        BRAHENVILLE = new City("Брехенвиль") ;
        BRAHENVILLE.setRailStation(new RailStation("Станция города " + BRAHENVILLE, 2));
    }

    static {
        DAVILON = new City("Давилон") ;
        Market market = new Market("Маркет города " + DAVILON, 0.2f);
        Stocks[] stocksName = Stocks.values();
        Random rnd = new Random();
        for(int i = 0; i < stocksName.length; ++i) {
            market.setStock(new Stock(rnd.nextFloat() * 300, stocksName[i].toString()));
        }
        DAVILON.setMarket(market);
        DAVILON.setRailStation(new RailStation("Станция города " + DAVILON, 2));
        DAVILON.getRailStations().get(0).setTrain(new Train("Поезд"));
    }

    static {
        GRABENBERG = new City("Грабенберг") ;
        Market market = new Market("Маркет города " + GRABENBERG, 0.12f);
        Stocks[] stocksName = Stocks.values();
        Random rnd = new Random();
        for(int i = 0; i < stocksName.length; ++i) {
            market.setStock(new Stock(rnd.nextFloat() * 300, stocksName[i].toString()));
        }
        GRABENBERG.setMarket(market);
    }

    static {
        SAN_KOMARIKA = new City("Сан-Комарика") ;
        Market market = new Market("Маркет города " + SAN_KOMARIKA, 0.18f);
        Stocks[] stocksName = Stocks.values();
        Random rnd = new Random();
        for(int i = 0; i < stocksName.length; ++i) {
            market.setStock(new Stock(rnd.nextFloat() * 300, stocksName[i].toString()));
        }
        SAN_KOMARIKA.setMarket(market);
    }

    private Cities() {}

    static public ArrayList<City> getAllCities() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(BRAHENVILLE);
        cities.add(DAVILON);
        cities.add(GRABENBERG);
        cities.add(SAN_KOMARIKA);
        return cities;
    }
}
