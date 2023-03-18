package locations;

import exceptions.AbsenceOfAnObjectException;
import exceptions.RocketDoesntWorkRuntimeException;

import java.util.ArrayList;
import java.util.Objects;

public class Space extends Location {
    private ArrayList<Planet> planets;
    private ArrayList<Rocket> rockets;

    public Space(String inputName) {
        super(inputName);
        rockets = new ArrayList<Rocket>();
        planets = new ArrayList<Planet>();
    }

    public void setRocket(Rocket inputRocket) {
        rockets.add(inputRocket);
    }

    public void setPlanet(Planet inputPlanet) {
        planets.add(inputPlanet);
    }

    public ArrayList<Rocket> getRockets() {
        return rockets;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void giveRocketToPlanet(Planet pl, Rocket rk) throws AbsenceOfAnObjectException, RocketDoesntWorkRuntimeException {
        if (planets.contains(pl)) {
            if (rockets.contains(rk)) {
                if (rk.isWorking()) {
                    rockets.remove(rk);
                    pl.setRocket(rk);
                    System.out.println("Ракета " + rk + " приземлилась на планету " + pl);
                } else
                    throw new RocketDoesntWorkRuntimeException(this, pl);
            } else
                throw new AbsenceOfAnObjectException("Нельзя отправить из " + this + " на планету " + pl + " ракету " + rk + ", которой нет в " + this);
        } else
            throw new AbsenceOfAnObjectException("Нельзя отправить ракету " + rk + " на планету " + pl + ", если в этой вселенной нет этой планеты");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Space space = (Space) o;
        return planets.equals(space.planets) && rockets.equals(space.rockets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), planets, rockets);
    }
}
