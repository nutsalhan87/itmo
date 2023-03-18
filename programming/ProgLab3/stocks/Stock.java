package stocks;

import java.util.Objects;

public class Stock {
    private float cost;
    private String name;

    public Stock(float inputCost, String inputName) {
        cost = inputCost;
        name = inputName;
    }

    public float getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Float.compare(stock.cost, cost) == 0 && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, name);
    }
}
