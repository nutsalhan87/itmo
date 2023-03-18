import humans.*;
import locations.Cities;
import timeOfDay.DayConditions;
import timeOfDay.TimeOfDay;

public class Main {
    public static void main(String[] args){
        TimeOfDay dayTime = new TimeOfDay(DayConditions.NIGHT);
        dayTime.setCondition(DayConditions.SUNRISE);

        MainCharacter mainHero = new MainCharacter("Скуперфильд", Cities.BRAHENVILLE.getRailStations().get(0), 3);
        mainHero.giveWearableThing(WearableThings.CYLINDER);
        mainHero.giveWearableThing(WearableThings.COAT);

        Human railStationHead = new Human("Железнодорожный начальник", Cities.BRAHENVILLE);
        railStationHead.changeLocation(Cities.BRAHENVILLE.getRailStations().get(0));
        railStationHead.toWakeSomeone(mainHero);

        Crowd crowd = new Crowd(Cities.DAVILON.getRailStations().get(0).getTrains().get(0)); // Толпа находится в поезде на станции в Давилоне
        Cities.DAVILON.getRailStations().get(0).giveTrainToAnotherStation(Cities.BRAHENVILLE.getRailStations().get(0), Cities.DAVILON.getRailStations().get(0).getTrains().get(0));
        crowd.changeLocation(Cities.BRAHENVILLE.getRailStations().get(0));

        mainHero.wear(WearableThings.CYLINDER);
        mainHero.thinkAboutSomething();

        crowd.changeLocation(Cities.BRAHENVILLE);
        mainHero.changeLocation(Cities.BRAHENVILLE);

        mainHero.lookAt(ThingsToLookAt.NEWSPAPER);
    }
}
