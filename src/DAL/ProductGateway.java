package DAL;

import Models.Product;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by SHAHED-PC on 7/21/2017.
 */
public class ProductGateway {
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
    public ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<Product>();
        String sql = "SELECT * FROM product";

        try (Connection connection =  connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                double price = resultSet.getDouble("currentPrice");
                Product product = new Product(id, name, url, price);
                //System.out.println("Fetching product info: "+name);
                products.add(product);
            }
        } catch (Exception exception) {
            //System.out.println("can't fetch products!");
            exception.printStackTrace();
        }

        return products;
    }
    public Product getProductById(int PoductId){
        Product product = null;
        String sql = "SELECT * FROM product WHERE id = "+PoductId+";";

        try (Connection connection =  connect(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                double price = resultSet.getDouble("price");
                product = new Product(id, name, url, price);

            }
        } catch (Exception exception) {

        }

        return product;
    }
    public boolean insert(Product product){
        String sql = "INSERT INTO product (name, url, currentPrice) VALUES(?, ?, ?);";

        try (Connection connection =  connect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getUrl());
            preparedStatement.setDouble(3, product.getCurrentPrice());
            int rowsEffected = preparedStatement.executeUpdate();
            //connection.commit();
            if (rowsEffected > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isAlreadyExist(Product product){
        String sql;
        sql = "SELECT * FROM product WHERE url = '"+product.getUrl()+"';";
        try (Connection connection =  connect();
             Statement statement  = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)){

            if(resultSet.next()) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean update(Product product){
        String sql = "UPDATE product SET name=?, currentPrice = ? WHERE url = ?";
        try (Connection connection =  connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getCurrentPrice());
            preparedStatement.setString(3, product.getUrl());

            int rowsEffected = preparedStatement.executeUpdate();
            //connection.commit();
            if (rowsEffected > 0) return true;
        } catch (Exception exception) {

        }
        return false;
    }
    public boolean delete(Product product){
        String sql = "DELETE product  WHERE id = ?";
        try (Connection connection =  connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, product.getId());

            int rowsEffected = preparedStatement.executeUpdate();
            //connection.commit();
            if (rowsEffected > 0) return true;
        } catch (Exception exception) {

        }
        return false;
    }
    public boolean delete(int productID){
        String sql = "DELETE product  WHERE id = ?";
        try (Connection connection =  connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);

            int rowsEffected = preparedStatement.executeUpdate();
            //connection.commit();
            if (rowsEffected > 0) return true;
        } catch (Exception exception) {

        }
        return false;
    }
}
