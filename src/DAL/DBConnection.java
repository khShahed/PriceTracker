package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by SHAHED-PC on 7/21/2017.
 */
//Singleton Design Pattern Applied Here
public class DBConnection {
    private Connection connection;
    private Statement statement;
    private static boolean isTableCreated = false;
    public DBConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:productTrace.db");
            //System.out.println("Database Connection Established.");
            if(!isTableCreated){
                createTables();
                //System.out.println("Tables created if not exist!");
                isTableCreated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables(){
        createProductTable();
        createPriceHistoryTable();
        createLogTable();
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createProductTable(){
        String sql = "CREATE TABLE IF NOT EXISTS product ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL,"+
                "url TEXT, "+
                "currentPrice REAL" +
                ");";
        executeStatement(sql);
    }
    private void createPriceHistoryTable(){
        String sql = "CREATE TABLE IF NOT EXISTS priceHistory("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL,"+
                "price REAL, "+
                "Product INTEGER, "+
                "FOREIGN KEY(product) REFERENCES product(id)" +
                ");";
        executeStatement(sql);
    }
    private void createLogTable(){
        String sql = "CREATE TABLE IF NOT EXISTS log("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dateTime TEXT NOT NULL,"+
                "description TEXT "+
                ");";
        executeStatement(sql);
    }
    private void executeStatement(String sql){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
