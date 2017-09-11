package DAL;

import Models.PriceHistory;
import Models.Product;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by SHAHED-PC on 7/21/2017.
 */
public class PriceHistoryGateway {
    public Connection connect(){
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:productTrace.db");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public boolean insert(PriceHistory priceHistory){
        String sql = "INSERT INTO priceHistory (date, price, product) VALUES(?, ?, ?);";

        try (Connection connection =  connect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, priceHistory.getDate());
            preparedStatement.setDouble(2, priceHistory.getPrice());
            preparedStatement.setInt(3, priceHistory.getProduct());
            int rowsEffected = preparedStatement.executeUpdate();
            //connection.commit();
            if (rowsEffected > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<PriceHistory> getPriceHistories(){
        ArrayList<PriceHistory> priceHistories = new ArrayList<PriceHistory>();
        String sql = "SELECT * FROM priceHistory";

        try (Connection connection =  connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                double price = resultSet.getDouble("price");
                int product = resultSet.getInt("product");
                PriceHistory priceHistory = new PriceHistory(id, date, price, product);
                priceHistories.add(priceHistory);
            }
        } catch (Exception exception) {

        }

        return priceHistories;
    }
    public ArrayList<PriceHistory> getPriceHistoriesByProuct(Product Product){
        ArrayList<PriceHistory> priceHistories = new ArrayList<PriceHistory>();
        String sql = "SELECT * FROM priceHistory WHERE product = "+Product.getId()+";";

        try (Connection connection =  connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                double price = resultSet.getDouble("price");
                int product = resultSet.getInt("product");
                PriceHistory priceHistory = new PriceHistory(id, date, price, product);
                priceHistories.add(priceHistory);
            }
        } catch (Exception exception) {

        }

        return priceHistories;
    }
    public ArrayList<PriceHistory> getPriceHistoriesByProuctId(int ProductId){
        ArrayList<PriceHistory> priceHistories = new ArrayList<PriceHistory>();
        String sql = "SELECT * FROM priceHistory WHERE product = "+ProductId+";";

        try (Connection connection =  connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                double price = resultSet.getDouble("price");
                int product = resultSet.getInt("product");
                PriceHistory priceHistory = new PriceHistory(id, date, price, product);
                priceHistories.add(priceHistory);
            }
        } catch (Exception exception) {

        }
        return priceHistories;
    }
    public boolean isHistoryAvailable(String date, int productId){
        String sql = "SELECT * FROM priceHistory WHERE product = "+productId+" AND date ='"+date+"';";

        try (Connection connection =  connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            if(!resultSet.next()){
                return false;
            }
            else {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
