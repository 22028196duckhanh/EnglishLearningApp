package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameDatabase {
    private static final String jdbcURL = "jdbc:sqlite:src/main/java/Resource/game.db";
    private static Connection connection = null;
    private static final int SUBJECT = 84;

    public static void init() throws SQLException {
        System.out.println("Loading...");
        connection = DriverManager.getConnection(jdbcURL);
        System.out.println("Let's go.");
    }

    public static void close() throws SQLException {
        connection.close();
    }

    private static int getSubject() {
        Random rd = new Random();
        return rd.nextInt(SUBJECT) + 1;
    }

   private static int getLevel() throws SQLException {
        ArrayList<Integer> listLevel = new ArrayList<>();
        int sj = getSubject();
        String sql_query = "SELECT * FROM level WHERE id_sj = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setInt(1, sj);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int lv = result.getInt("id_lv");
            listLevel.add(lv);
        }
        result.close();
        statement.close();
        if (listLevel.isEmpty()) return 100;
        Random rd = new Random();
        return listLevel.get(rd.nextInt(listLevel.size()));
    }

    public static int getIdQuestion() throws SQLException {
        ArrayList<Integer> listQuestion = new ArrayList<>();
        int lv = getLevel();
        String sql_query = "SELECT * FROM question WHERE id_lv = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setInt(1, lv);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt("id_qt");
            listQuestion.add(id);
        }
        result.close();
        statement.close();
        if (listQuestion.isEmpty()) return 100;
        Random rd = new Random();
        return listQuestion.get(rd.nextInt(listQuestion.size()));
    }

    public static String getQuestion(int id) throws SQLException {
        String ques = "";
        String sql_query = "SELECT * FROM question WHERE id_qt = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            ques = result.getString("content");
        }
        ques = ques.replaceAll("<br/>","\n");
        result.close();
        statement.close();
        return ques;
    }

    public static Map<String, Boolean> getAnswer(int id) throws SQLException {
        Map<String, Boolean> ans = new HashMap<>();
        String sql_query = "SELECT * FROM answer WHERE id_qt = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setInt(1, id);
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
