package Server;

import java.security.KeyPair;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameDatabase {
    private static final String jdbcURL = "jdbc:sqlite:src/Resource/game.db";
    private static Connection connection = null;
    private static final int SUBJECT = 84;

    public void init() throws SQLException {
        System.out.println("Loading...");
        connection = DriverManager.getConnection(jdbcURL);
        System.out.println("Database connected!");
    }

    public void close() throws SQLException {
        connection.close();
        System.out.println("Database disconnected!");
    }

    private int getSubject() {
        Random rd = new Random();
        return rd.nextInt(SUBJECT) + 1;
    }

   private int getLevel() throws SQLException {
        ArrayList<Integer> listLevel = new ArrayList<>();
        String sj = String.valueOf(getSubject());
        String sql_query = "SELECT * FROM level WHERE id_sj = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, sj);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int lv = result.getInt("id_lv");
            listLevel.add(lv);
        }
        result.close();
        statement.close();
        Random rd = new Random();
        return listLevel.get(rd.nextInt(listLevel.size()));
    }

    public int getIdQuestion() throws SQLException {
        ArrayList<Integer> listQuestion = new ArrayList<>();
        String lv = String.valueOf(getLevel());
        String sql_query = "SELECT * FROM question WHERE id_lv = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, lv);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt("id_qt");
            listQuestion.add(id);
        }
        result.close();
        statement.close();
        Random rd = new Random();
        return listQuestion.get(rd.nextInt(listQuestion.size()));
    }

    public String getQuestion(int id) throws SQLException {
        String ques = "";
        String sql_query = "SELECT * FROM question WHERE id_qt = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, String.valueOf(id));
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            ques = result.getString("content");
        }
        result.close();
        statement.close();
        return ques;
    }

    public Map<String, Boolean> getAnswer(int id) throws SQLException {
        Map<String, Boolean> ans = new HashMap<>();
        String sql_query = "SELECT * FROM answer WHERE id_qt = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, String.valueOf(id));
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String tmp1 = result.getString("as_content");
            Boolean tmp2 = result.getBoolean("isright");
            ans.put(tmp1, tmp2);
        }
        result.close();
        statement.close();
        return ans;
    }
}
