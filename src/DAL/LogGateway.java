package DAL;

import Models.Log;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by SHAHED-PC on 8/8/2017.
 */
public class LogGateway {
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
    public boolean insert(String description){
        String sql = "INSERT INTO log (dateTime, description) VALUES(?, ?);";
        String dateTime = LocalDate.now()+" at "+ LocalTime.now();
        try (Connection connection =  connect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, dateTime);
            preparedStatement.setString(2,description);
            int rowsEffected = preparedStatement.executeUpdate();
            //connection.commit();
            if (rowsEffected > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Log> getLoges(){
        ArrayList<Log> logs = new ArrayList<Log>();
        String sql = "SELECT * FROM log";

        try (Connection connection =  connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dateTime = resultSet.getString("dateTIme");
                String description = resultSet.getString("description");
                Log log = new Log(id, dateTime, description);
                logs.add(log);
            }
        } catch (Exception exception) {

        }

        return logs;
    }
}
