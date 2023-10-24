package Server;

import javafx.util.Pair;

import java.sql.*;

public class FillGame {
    public static Pair<String, String> getques() throws ClassNotFoundException, SQLException {
        Connection connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:fillinblank.sqlite");
            Statement statement = connection.createStatement();

            String query = String.format("SELECT * FROM Fillinblank WHERE ID = %d ", (int) (Math.random()*252 + 1)) ;
            ResultSet resultSet = statement.executeQuery(query);

            String column2Value = new String();
            String column3Value = new String();
            if (resultSet.next()) {
                int column1Value = resultSet.getInt("ID");
                column2Value = resultSet.getString("question");
                column3Value = resultSet.getString("answer");
            }
            return new Pair<String, String>(column2Value, column3Value);
    }
}