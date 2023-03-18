package locations;

import java.util.ArrayList;
import java.util.Objects;

import stocks.Stock;

public class Market extends Location {
    private float tax;
    private ArrayList<Stock> stocks;

    public Market(String inputName, float inputTax) {
        super(inputName);
        tax = inputTax;
        stocks = new ArrayList<Stock>();
    }

    public float getTax() {
        return tax;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStock(Stock stock) {
        stocks.add(stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Market market = (Market) o;
        return Float.compare(market.tax, tax) == 0 && stocks.equals(market.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tax, stocks);
    }
}
