package locations;

import java.util.ArrayList;
import java.util.Objects;

public class RailStation extends Location{
    private ArrayList<Train> trains;
    private int capacity;

    public RailStation(String inputName, int inputCapacity) {
        super(inputName);
        capacity = inputCapacity;
        trains = new ArrayList<Train>();
    }

    public int getCapacity() {
        return capacity;
    }

    public void giveTrainToAnotherStation(RailStation railStation, Train train) {
        if (railStation.getTrains().size() < railStation.getCapacity()) {
            System.out.println(this.getTrains().get(0).toString() + " переместился со станции " + this + " на станцию " + railStation);
            railStation.setTrain(train);
            this.trains.remove(train);
        }
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public void setTrain(Train train) {
        if (trains.size() < capacity) {
            trains.add(train);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RailStation that = (RailStation) o;
        return capacity == that.capacity && trains.equals(that.trains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trains, capacity);
    }
}
