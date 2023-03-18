package locations;

import java.util.ArrayList;
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
    public String toString() {
        return name;
    }
}
