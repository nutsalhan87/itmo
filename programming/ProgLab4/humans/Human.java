package humans;

import locations.Cities;
import locations.Location;

import java.util.Objects;

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
        System.out.println(this + " проснулся");
        isSleep = false;
    }

    public void sleep() {
        System.out.println(this + " заснул");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Human human = (Human) o;
        return isSleep == human.isSleep && name.equals(human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, isSleep);
    }
}
