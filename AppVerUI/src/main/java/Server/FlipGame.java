package Server;

import java.sql.*;

public class FlipGame {
    public static String getImage() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:fillinblank.sqlite");
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM flipCard WHERE ID = %d ", (int) (Math.random() * 187 + 1));
            ResultSet resultSet = statement.executeQuery(query);

            String column2Value = "";
            if (resultSet.next()) {
                column2Value = resultSet.getString("imageLink");
            }
            return column2Value;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
