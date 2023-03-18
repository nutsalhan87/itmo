package humans;

import locations.City;
import locations.Location;
import locations.Cities;
import locations.Market;

import stocks.Stock;

import java.util.List;
import java.util.ArrayList;

public class MainCharacter extends Human implements CanThinkAboutStocks {
    private ArrayList<WearableThings> wearableThings;
    private WearableThings wearsNow;
    private int maxAmount;

    public MainCharacter(String inputName, Location inputLocation, int inputMaxAmount) {
        super(inputName, inputLocation);
        maxAmount = inputMaxAmount;
        wearableThings = new ArrayList<>();
    }

    public void giveWearableThing(WearableThings wearableThing) {
        if (wearableThings.size() < maxAmount) {
            wearableThings.add(wearableThing);
        }
    }

    public ArrayList<WearableThings> getWearableThings() {
        return wearableThings;
    }

    public void wear(WearableThings wearableThing) {
        if (wearableThings.contains(wearableThing)) {
            wearsNow = wearableThing;
            System.out.println(this + " надевает на себя " + wearableThing.toString());
        }
    }

    public void lookAt(ThingsToLookAt thingToLookAt) {
        System.out.println(this + " посмотрел на объект под названием " + thingToLookAt);
        if (thingToLookAt == ThingsToLookAt.NEWSPAPER)
        {
            System.out.println("Так как " + this + " посмотрел на объект под названием " + thingToLookAt +
                    ", он начал считать в голове, в каком городе купит акции");
            System.out.println("После некоторых размышлений " + this + " пришел к выводу, что в городе " +
                    calculateWhereTheBestOffer().toString() + " можно было бы заработать больше всего");
        }
    }

    public City calculateWhereTheBestOffer() {
        List<City> cities = Cities.getAllCities();
        float bestOffer = 0, currentOffer = 0;
        City bestCity = new City("New City");
        for (int i = 0; i < cities.size(); ++i) {
            currentOffer = 0;
            City currentCity = cities.get(i);
            if (currentCity.getMarkets().size() == 0) {
                continue;
            }
            Market currentMarket = currentCity.getMarkets().get(0);
            ArrayList<Stock> currentStocks = currentMarket.getStocks();
            for (int j = 0; j < currentStocks.size(); ++j) {
                currentOffer += currentStocks.get(j).getCost();
            }
            currentOffer -= currentOffer * currentMarket.getTax();
            if (bestOffer <= currentOffer) {
                bestOffer = currentOffer;
                bestCity = currentCity;
            }
        }
        return bestCity;
    }

    public void thinkAboutSomething() {
        System.out.println(this + " задумался");
    }

    @Override
    public String toString() {
        return name;
    }
}
