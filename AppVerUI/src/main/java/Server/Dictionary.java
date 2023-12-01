package Server;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dictionary {

    public abstract void init() throws SQLException;

    public abstract void close() throws SQLException;

    public abstract ArrayList<Word> getAllWords() throws SQLException;

    public abstract ArrayList<String> getAllWordTargets() throws SQLException;

    public abstract ArrayList<String> searchWord(String prefix) throws SQLException;

    public abstract String lookUpWord(String wordTarget) throws SQLException;

    public abstract boolean insertWord(String wordTarget, String wordExplain) throws SQLException;

    public abstract boolean deleteWord(String wordTarget) throws SQLException;

    public abstract boolean modifyWord(String wordTarget, String newExplain) throws SQLException;

    public String printAsTable(ArrayList<Word> wordList, int st, int en) {
        int idx = 1;
        StringBuilder res = new StringBuilder(String.format("%-8s| %-20s| %s\n", "No", "English", "Vietnamese"));
        for (int i = st; i <= en; i++) {
            res.append(String.format("%-8d| %-20s| %s\n", idx,
                    wordList.get(i).getWordTarget(), wordList.get(i).getWordExplain()));
            idx++;
        }
        return res.toString();
    }

    public String displayAllWords() throws SQLException {
        ArrayList<Word> list = getAllWords();
        return printAsTable(list, 0, list.size() - 1);
    }

    public void exportToFile() {
    }

    public abstract String getFullExplain(String selectedWord) throws SQLException;

    public boolean editHtml(String word, String change) throws SQLException {
        return true;
    }

    public boolean setDefault(String word) throws SQLException {
        return true;
    }
}
