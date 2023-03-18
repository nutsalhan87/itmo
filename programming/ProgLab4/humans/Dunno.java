package humans;

import exceptions.AbsenceOfAnObjectException;
import exceptions.RocketDoesntWorkRuntimeException;
import locations.*;
import noise.NoiseSources;
import noise.Noisy;

import java.util.Objects;

public class Dunno extends Human {
    public Happiness happiness;

    public static class Happiness {
        private int happiness;

        public Happiness() {
            happiness = 50;
        }

        public int getHappiness() {
            return happiness;
        }

        public void changeHappinessOnValue(int value) {
            if (((float) value) / ((float) happiness) >= 0.5) {
                Rejoice();
            }
            if (((float) value) / ((float) happiness) <= -0.5) {
                BecomeDiscouraged();
            }

            happiness += value;
            if (happiness > 100) happiness = 100;
            if (happiness < 0) happiness = 0;
        }

        private void Rejoice() {
            System.out.println("Его лицо расплылось в счастливой улыбке, а внутри словно что-то затрепетало, заметалось от радости");
        }

        private void BecomeDiscouraged() {
            System.out.println("Его лицо стало в крайней степени угрюмым, а злость наполнила его глаза");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Happiness happiness1 = (Happiness) o;
            return happiness == happiness1.happiness;
        }

        @Override
        public int hashCode() {
            return Objects.hash(happiness);
        }
    }

    public Dunno(String inputName, Location loc) {
        super(inputName, loc);
        happiness = new Happiness();
    }

    private NoiseSources hear(Noisy lCreateNoise) {
        if (lCreateNoise.createNoise().getNoiseLevel() > 10) {
//            if (this.location.equals(lCreateNoise.createNoise().getObj())) {
            if (location instanceof Rocket && location.equals(lCreateNoise.createNoise().getObj()) || location instanceof Rocket.Shelter && location.equals(((Rocket) lCreateNoise.createNoise().getObj()).shelter)) {
                System.out.println(this + " услышал шум двигателя");
                return NoiseSources.ENGINE;
            } else {
                System.out.println(this + " услышал некий шум");
                return NoiseSources.SOMETHING;
            }
        } else {
            System.out.println("Нечто издало звук, но " + this + " ничего не услышал");
            return NoiseSources.NOTHING;
        }
    }

    private boolean feelZeroGravity(Space space) {
        // Вернуть true, если Незнайка в ракете или в убежище, а эта ракета в космосе
        if (location instanceof Rocket && space.getRockets().contains(location) || location instanceof Rocket.Shelter && space.getRockets().contains(((Rocket.Shelter) location).getOuterRocket())) {
            System.out.println(this + " почувствовал невесомость");
            return true;
        } else return false;
    }

    public void initRocketActions(Space space, Noisy lCreateNoise) throws AbsenceOfAnObjectException {
        if (feelZeroGravity(space) && hear(lCreateNoise) == NoiseSources.ENGINE) {
            say("Ага! Космический корабль в полете!");
            if (location instanceof Rocket) imagineSituationInMind((Rocket) location);
            else if (location instanceof Rocket.Shelter)
                imagineSituationInMind(((Rocket.Shelter) location).getOuterRocket());
            say("Все получилось точно, как я рассчитал!");
            this.happiness.changeHappinessOnValue(100);
            if (location instanceof Rocket) admitToDoono((Rocket) location);
            else if (location instanceof Rocket.Shelter) admitToDoono(((Rocket.Shelter) location).getOuterRocket());
        }
    }

    private void imagineSituationInMind(Rocket rk) throws AbsenceOfAnObjectException {
        System.out.println(this + " воссоздал такую ситуацию у себя в голове:");
        this.sleep();
        Space space = new Space("Космос");
        Planet earth = new Planet("Земля");
        space.setPlanet(earth);

        Crowd shorties = new Crowd(earth) {
            @Override
            public String toString() {
                return "Коротышки";
            }
        };
        class Doono extends Human {
            public Doono(String inputName, Location loc) {
                super(inputName, loc);
            }

            public void turnOnRocket(Rocket rk) {
                rk.turnOn();
            }

            public void say(String toSay) {
                System.out.println("Незнайка сказал: " + toSay);
            }
        }
        Doono doono = new Doono("Знайка", earth);

        rk.turnOff();
        earth.setRocket(rk);
        shorties.changeLocation(rk);
        doono.changeLocation(rk);
        try {
            earth.giveRocketToSpace(space, rk);
        } catch (RocketDoesntWorkRuntimeException exc) {
            System.out.println(exc.getMessage());
            doono.say("Ах да, ракету сначала нужно включить!");
            doono.turnOnRocket(rk);
            earth.giveRocketToSpace(space, rk);
        }
        this.wakeUp();
    }

    private void admitToDoono(Rocket rk) {
        Human doono = new Human("Знайка", rk);
        if (location == doono.location) {
            say("Признаюсь тебе, " + doono + ", что без спросу залез в ракету");
        } else {
            System.out.println(this + " хотел признаться персонажу " + doono + ", что без спросу залез в ракету, но не нашел его");
            say("Подожду немного, а потом попробую найти его и признаться ему");
        }
    }

    public void say(String toSay) {
        System.out.println(this + " сказал: \"" + toSay + "\"");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Dunno dunno = (Dunno) o;
        return happiness.equals(dunno.happiness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), happiness);
    }
}
