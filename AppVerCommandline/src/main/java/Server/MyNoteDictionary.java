package Server;

import java.io.*;
import java.util.ArrayList;

public class MyNoteDictionary extends Dictionary{

    private static final ArrayList<Word> words = new ArrayList<>();
    private static final Trie trie = new Trie();
    private static final String path = "src/main/java/Resource/note.txt";

    @Override
    public void init() {
        System.out.println("Initializing...");
        insertFromFile();
        System.out.println("Init successfully!");
    }

    @Override
    public void close() {
        System.out.println("Saving...");
        exportToFile();
        System.out.println("See you again.");
    }

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

    private void insertFromFile() {
        try {
            System.out.println("Loading data...");
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tmp = line.split("\\|");
                Word word = new Word(tmp[0].trim(),tmp[1].trim());
                words.add(word);
                trie.addWord(word.getWordTarget());
            }
            System.out.println("Successful.");
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    @Override
    public void exportToFile() {
        try {
            System.out.println("Exporting...");
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : words) {
                bufferedWriter.write(word.getWordTarget() + "|" + word.getWordExplain());
                bufferedWriter.newLine();
            }
            System.out.println("Successful.");
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}
