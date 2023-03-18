package locations;

import exceptions.AbsenceOfAnObjectException;
import exceptions.RocketDoesntWorkRuntimeException;

import java.util.ArrayList;
import java.util.Objects;

public class Planet extends Location {
    private ArrayList<City> cities;
    private ArrayList<Rocket> rockets;

    public Planet(String inputName) {
        super(inputName);
        cities = new ArrayList<>();
        rockets = new ArrayList<>();
    }

    public Planet(String inputName, ArrayList<City> inputCities) {
        super(inputName);
        cities = inputCities;
        rockets = new ArrayList<>();
    }

    public void setCity(City inputCity) {
        cities.add(inputCity);
    }

    public void setRocket(Rocket inputRocket) {
        rockets.add(inputRocket);
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Rocket> getRockets() {
        return rockets;
    }

    public void giveRocketToSpace(Space sp, Rocket rk) throws AbsenceOfAnObjectException, RocketDoesntWorkRuntimeException {
        if (sp.getPlanets().contains(this)) {
            if (rockets.contains(rk)) {
                if (rk.isWorking()) {
                    rockets.remove(rk);
                    sp.setRocket(rk);
                    System.out.println("Ракета " + rk + " отправилась в космос");
                } else {
                    throw new RocketDoesntWorkRuntimeException(this, sp);
                }
            } else
                throw new AbsenceOfAnObjectException("Нельзя отправить из планеты " + this + " в " + sp + " ракету, которой нет на планете " + this);
        } else
            throw new AbsenceOfAnObjectException("Нельзя отправить в " + sp + " ракету, так как " + this + " в другой вселенной");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Planet planet = (Planet) o;
        return cities.equals(planet.cities) && rockets.equals(planet.rockets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cities, rockets);
    }
}
