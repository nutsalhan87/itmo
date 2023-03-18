package humans;

import locations.Cities;
import locations.Location;

public class Human extends Humans implements Sleepable{
    protected String name;
    private boolean isSleep;

    public Human(String inputName, Location inputLocation) {
        super(inputLocation);
        name = inputName;
        isSleep = false;
    }

    public boolean getSleepCondition() {
        return isSleep;
    }

    public void wakeUp() {
        isSleep = false;
    }

    public void sleep() {
        isSleep = true;
    }

    public void toWakeSomeone(Human human) {
        if(!isSleep) {
            human.wakeUp();
            if(super.location.equals(Cities.BRAHENVILLE.getRailStations().get(0))) {
                System.out.println("Так как спать на станции запрещено, " + this + " разбудил человека по имени " + human);
            }
            else {
                System.out.println(this + " разбудил человека по имени " + human);
            }
        }
        else {
            System.out.println(this + " попытался разбудить человека по имени " + human + ", а он и не спал");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
