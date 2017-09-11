package Models;

/**
 * Created by SHAHED-PC on 7/20/2017.
 */
public class PriceHistory {
    private int id;
    private String date;
    private double price;
    private int product;

    public PriceHistory(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public PriceHistory(int id, String date, double price, int product) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.product = product;
    }

    public PriceHistory(String date, double price, int product) {
        this.date = date;
        this.price = price;
        this.product = product;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getProduct() {
        return product;
    }
}
