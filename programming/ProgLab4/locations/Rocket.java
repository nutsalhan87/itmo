package locations;

import noise.Noise;
import noise.Noisy;

import java.util.Objects;

public class Rocket extends Location {
    public Engine engine;
    public Shelter shelter;

    public class Engine implements Noisy {
        private boolean isWorking;

        private Engine() {
            isWorking = false;
        }

        public Noise createNoise() {
            if (isWorking) {
                return new Noise(12, Rocket.this);
            } else return new Noise(0, Rocket.this);
        }
    }

    public class Shelter extends Location {
        private Shelter(String inputName) {
            super(inputName);
        }

        public Rocket getOuterRocket() {
            return Rocket.this;
        }
    }

    public Rocket(String inputName) {
        super(inputName);
        engine = new Engine();
        shelter = new Shelter("Укрытие в ракете " + inputName);
    }

    public void turnOn() {
        System.out.println("Ракета " + this + " включена");
        engine.isWorking = true;
    }

    public void turnOff() {
        System.out.println("Ракета " + this + " выключена");
        engine.isWorking = false;
    }

    public boolean isWorking() {
        return engine.isWorking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(engine, rocket.engine) && Objects.equals(shelter, rocket.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), engine, shelter);
    }
}
