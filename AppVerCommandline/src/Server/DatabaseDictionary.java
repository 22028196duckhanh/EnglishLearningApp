package Server;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseDictionary extends Dictionary{
    private static final String jdbcURL = "jdbc:sqlite:src/Resource/dictionary.db";
    private static Connection connection = null;

    @Override
    public void init() throws SQLException {
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(jdbcURL);
        System.out.println("Database connected!");
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
        String sql_query = "SELECT * FROM av WHERE word LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, prefix + "%");
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
    public String lookUpWord(String wordTarget) throws SQLException {
        String sql_query = "SELECT * FROM av WHERE word = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, wordTarget);
        ResultSet result = statement.executeQuery();
        String meaning = result.getString("description");
        result.close();
        statement.close();
        if (meaning == null) return "Not Found";
        return meaning;
    }

    @Override
    public boolean insertWord(String wordTarget, String wordExplain) throws SQLException {
        boolean success = true;
        ResultSet result = null;
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
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            String sql_query = "DELETE FROM av WHERE word = ?";
            statement = connection.prepareStatement(sql_query);
            statement.setString(1, wordTarget);
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            statement.close();
        }
        return success;
    }

    @Override
    public boolean modifyWord(String wordTarget, String newExplain) throws SQLException {
        boolean success = true;
        ResultSet result = null;
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
            statement.close();
        }
        return success;
    }
}
