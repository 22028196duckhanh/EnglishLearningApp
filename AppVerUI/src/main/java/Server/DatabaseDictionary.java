package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class DatabaseDictionary extends Dictionary {
    private static final String jdbcURL = "jdbc:sqlite:src/main/resources/Utils/data/dictionary.db";
    private static Connection connection = null;

    @Override
    public void init() throws SQLException {
        //System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(jdbcURL);
        //System.out.println("Database connected!");
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        System.out.println("Database disconnected!");
    }

    @Override
    public ArrayList<Word> getAllWords() throws SQLException {
        ArrayList<Word> words = new ArrayList<>();
        String sql_query = "SELECT * FROM av";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String word = result.getString("word");
            String meaning = result.getString("description");
            Word tmp = new Word(word, meaning);
            words.add(tmp);
        }
        result.close();
        statement.close();
        return words;
    }

    @Override
    public ArrayList<String> getAllWordTargets() throws SQLException {
        ArrayList<String> words = new ArrayList<>();
        String sql_query = "SELECT * FROM av";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String word = result.getString("word");
            words.add(word);
        }
        result.close();
        statement.close();
        return words;
    }

    @Override
    public ArrayList<String> searchWord(String prefix) throws SQLException {
        ArrayList<String> words = new ArrayList<>();
        String sql_query = "SELECT word FROM av WHERE word LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, prefix + '%');
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String word = result.getString("word");
            words.add(word);
        }
        result.close();
        statement.close();
        return words;
    }

    public void searchHighlight(LinkedList<Pair<String, String>> words) throws SQLException {
        String sql_query = "SELECT * FROM av WHERE highlight = 1";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String word = result.getString("word");
            String description = result.getString("description");
            words.add(new Pair<>(word, description));
        }
        result.close();
        statement.close();
    }

    @Override
    public String lookUpWord(String wordTarget) throws SQLException {
        String sql_query = "SELECT * FROM av WHERE word = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, wordTarget);
        ResultSet result = statement.executeQuery();
        String meaning = result.getString("description");
        String pronoun = result.getString("pronounce");
        result.close();
        statement.close();
        if (meaning == null) return "Not Found";
        return "/" + pronoun + "/" + "\n" + meaning;
    }

    public boolean insertWord(String wordTarget, String wordExplain) throws SQLException {
        boolean success = true;
        PreparedStatement statement = null;
        try {
            String sql_query = "INSERT INTO av (word, description) VALUES (?, ?)";
            statement = connection.prepareStatement(sql_query);
            statement.setString(1, wordTarget);
            statement.setString(2, wordExplain);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            assert statement != null;
            statement.close();
        }
        return success;
    }

    @Override
    public boolean deleteWord(String wordTarget) throws SQLException {
        boolean success = true;
        PreparedStatement statement = null;
        try {
            String sql_query = "DELETE FROM av WHERE word = ?";
            statement = connection.prepareStatement(sql_query);
            statement.setString(1, wordTarget);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            assert statement != null;
            statement.close();
        }
        return success;
    }

    @Override
    public boolean modifyWord(String wordTarget, String newExplain) throws SQLException {
        boolean success = true;
        PreparedStatement statement = null;
        try {
            String sql_query = "UPDATE av SET description = ? WHERE word = ?";
            statement = connection.prepareStatement(sql_query);
            statement.setString(2, wordTarget);
            statement.setString(1, newExplain);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            assert statement != null;
            statement.close();
        }
        return success;
    }

    public String getFullExplain(String word) throws SQLException {
        String sql_query = "SELECT * FROM av WHERE word = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, word);
        ResultSet result = statement.executeQuery();
        String html = result.getString("html");
        String htmlChange = result.getString("change_html");
        result.close();
        statement.close();
        if (htmlChange == null && html == null) return "";
        else if (htmlChange == null) return html;
        return htmlChange;
    }

    public boolean setDefault(String word) throws SQLException {
        boolean success = true;
        PreparedStatement statement = null;
        try {
            String sql_query = "UPDATE av SET change_html = NULL WHERE word = ?";
            statement = connection.prepareStatement(sql_query);
            statement.setString(1, word);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            assert statement != null;
            statement.close();
        }
        return success;
    }

    public void setHighlight(String word) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT highlight FROM av WHERE word = ?");
        statement.setString(1, word);
        ResultSet result = statement.executeQuery();
        int hi = result.getInt("highlight");
        try {
            String sql_query = "UPDATE av SET highlight = " + (hi == 1 ? 0 : 1) + " WHERE word = ?";
            statement = connection.prepareStatement(sql_query);
            statement.setString(1, word);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getHighlight(String word) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT highlight FROM av WHERE word = ?");
        statement.setString(1, word);
        ResultSet result = statement.executeQuery();
        return result.getInt("highlight");
    }

    public boolean editHtml(String word, String change) throws SQLException {
        boolean success = true;
        PreparedStatement statement = null;
        try {
            String sql_query = "UPDATE av SET change_html = ? WHERE word = ?";
            statement = connection.prepareStatement(sql_query);
            statement.setString(2, word);
            statement.setString(1, change);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            assert statement != null;
            statement.close();
        }
        return success;
    }

    public boolean addWord(String word, String pronounce, String type, String meaning) throws SQLException {
        boolean success = true;
        PreparedStatement statement = null;
        if (!lookUpWord(word).equals("Not Found")) {
            return false;
        }
        try {
            String sql_query = "INSERT INTO av (word,html,description,pronounce) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql_query);
            statement.setString(1, word);
            statement.setString(2, "<h1>"+word+"</h1><h3><i>"+pronounce+"</i></h3><h2>"+type+"</h2><ul><li>"+meaning+"</li></ul>");
            statement.setString(3, type +": " +meaning);
            statement.setString(4, pronounce);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            assert statement != null;
            statement.close();
        }
        return success;
    }
}
