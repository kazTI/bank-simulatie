package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class YoutubeTutorial {

    public static void main(String args[]) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://145.24.222.39:8008/bank", "bank", "dPZgvJT5yq4P4ERc");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {
                log(resultSet.getString("first_name"));
                log(resultSet.getString("last_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void log(String message) {
        System.out.println(message);
    }
}
