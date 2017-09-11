package Models;

/**
 * Created by SHAHED-PC on 7/20/2017.
 */
public class Product {
    private int id;
    private String name;
    private String url;
    private double currentPrice;

    public Product(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Product(int id, String name, String url, double currentPrice) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.currentPrice = currentPrice;
    }

    public Product(String name, String url, double currentPrice) {
        this.name = name;
        this.url = url;
        this.currentPrice = currentPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }
}
