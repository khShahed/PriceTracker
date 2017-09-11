package BLL;

import DAL.DBConnection;
import DAL.LogGateway;
import DAL.PriceHistoryGateway;
import DAL.ProductGateway;
import Models.PriceHistory;
import Models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by SHAHED-PC on 8/8/2017.
 */
public class UpdateRecords implements Runnable {
    @Override
    public void run() {
        new DBConnection();
        LogGateway logGateway= new LogGateway();
        ProductGateway productGateway = new ProductGateway();
        PriceHistoryGateway priceHistoryGateway = new PriceHistoryGateway();
       // while (true) {
            List<Product> productList = productGateway.getProducts();
            String date = LocalDate.now().toString();
        //System.out.println(date);
        //System.out.println(productList.size());
            for (Product product : productList) {
                //System.out.println(product.getName());
                //System.out.println(priceHistoryGateway.isHistoryAvailable(date, product.getId()));
                if (!priceHistoryGateway.isHistoryAvailable(date, product.getId())) {
                    Double currentPrice = getCurrentPrice(product.getUrl());
                    PriceHistory priceHistory = new PriceHistory(date, currentPrice, product.getId());
                    if (priceHistoryGateway.insert(priceHistory)) {
                        logGateway.insert(product.getName() + "'s price information added to price history.");
                    }
                }
            }
            /*try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
    public double getCurrentPrice(String url){
        double currentPrice = 0;
        Document document = null;
        try {
            document = Jsoup.connect(url).userAgent("Chrome").timeout(5000).get();
        } catch (Exception e) {
        }
        //Fetching Price
        try {
            Elements elements = document.getElementsByAttributeValue("dir","ltr");
            //System.out.println( elements.size());
            if(elements.size()==2){
                currentPrice = Double.parseDouble(elements.get(0).attr("data-price"));
            }
            else if(elements.size()==1){
                currentPrice = Double.parseDouble(elements.get(0).attr("data-price"));
            }
        }catch (Exception exception){
            return  0;
        }
        return currentPrice;
    }

}
