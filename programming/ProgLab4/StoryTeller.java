import exceptions.AbsenceOfAnObjectException;
import humans.Dunno;
import locations.*;
import timeOfDay.DayConditions;
import timeOfDay.TimeOfDay;

public class StoryTeller {
    public static void main(String[] args) {
        Space space = new Space("Космос");
        Planet earth = new Planet("Земля", Cities.getAllCities());
        Planet moon = new Planet("Луна");
        space.setPlanet(moon);
        space.setPlanet(earth);

        Rocket rocket = new Rocket("Союз-9000");
        earth.setRocket(rocket);

        Dunno dunno = new Dunno("Незнайка", earth);

        TimeOfDay timeOfDay = new TimeOfDay(DayConditions.EVENING);

        dunno.changeLocation(rocket.shelter);
        rocket.turnOn();
        dunno.sleep();
        try {
            earth.giveRocketToSpace(space, rocket);
        } catch (AbsenceOfAnObjectException exc) {
            System.out.println(exc.getMessage());
        }
        timeOfDay.setCondition(DayConditions.NIGHT);
        dunno.wakeUp();
        try {
            dunno.initRocketActions(space, rocket.engine::createNoise);
        } catch (AbsenceOfAnObjectException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
