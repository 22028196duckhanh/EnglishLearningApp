package Server;

import java.util.ArrayList;

public class MyNoteDictionary extends Dictionary{

    private static final ArrayList<Word> words = new ArrayList<>();
    private static final Trie trie = new Trie();

    @Override
    public ArrayList<Word> getAllWords() {
        return words;
    }

    @Override
    public ArrayList<String> getAllWordTargets() {
        ArrayList<String> res = new ArrayList<>();
        for (Word word : words) {
            res.add(word.getWordTarget());
        }
        return res;
    }

    @Override
    public ArrayList<String> searchWord(String prefix) {
        return trie.autoComplete(prefix);
    }

    @Override
    public String lookUpWord(String wordTarget) {
        int pos = location(wordTarget);
        if (pos < 0) return "Not Found";
        return words.get(pos).getWordExplain();
    }

    @Override
    public boolean insertWord(String wordTarget, String wordExplain) {
        int id = location(wordTarget);
        if (id >= 0) return false;
        Word tmp = new Word(wordTarget, wordExplain);
        words.add(tmp);
        trie.addWord(wordTarget);
        words.sort(new SortByWord());
        return true;
    }

    @Override
    public boolean deleteWord(String wordTarget) {
        int pos = location(wordTarget);
        if (pos < 0) return false;
        words.remove(pos);
        trie.deleteWord(wordTarget);
        return true;
    }

    @Override
    public boolean modifyWord(String wordTarget, String newExplain) {
        int id = location(wordTarget);
        if (id < 0) return false;
        words.get(id).setWordExplain(newExplain);
        return true;
    }

    public int location(String key) {
        try {
            int left = 0;
            int right = words.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = words.get(mid).getWordTarget().compareTo(key);
                if (res == 0) return mid;
                if (res <= 0) left = mid + 1;
                else right = mid - 1;
            }
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
        return -1;
    }
}
