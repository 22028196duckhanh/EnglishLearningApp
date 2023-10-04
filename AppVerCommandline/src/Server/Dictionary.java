package Server;

import java.util.ArrayList;

public abstract class Dictionary {

    public abstract void init();

    public abstract void close();

    public abstract ArrayList<Word> getAllWords();

    public abstract ArrayList<String> getAllWordTargets();

    public abstract ArrayList<String> searchWord(String prefix);

    public abstract String lookUpWord(String wordTarget);

    public abstract boolean insertWord(String wordTarget, String wordExplain);

    public abstract boolean deleteWord(String wordTarget);

    public abstract boolean modifyWord(String wordTarget, String newExplain);

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

    public String displayAllWords() {
        ArrayList<Word> list = getAllWords();
        return printAsTable(list, 0, list.size());
    }
}
