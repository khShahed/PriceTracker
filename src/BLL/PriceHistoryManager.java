package BLL;

import DAL.PriceHistoryGateway;
import Models.PriceHistory;

import java.util.ArrayList;

/**
 * Created by SHAHED-PC on 7/21/2017.
 */
public class PriceHistoryManager {
    public ArrayList<PriceHistory> getPriceHistoriesByProuctId(int ProductId){
       return new PriceHistoryGateway().getPriceHistoriesByProuctId(ProductId);
    }
}
