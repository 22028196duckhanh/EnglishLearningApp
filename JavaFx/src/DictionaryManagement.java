import java.io.*;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private Trie trie = new Trie();

    public void insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        int numOfWord = sc.nextInt();
        String tmp = sc.nextLine();
        for (int i = 0; i < numOfWord; i++) {
            String wordTarget = sc.nextLine();
            String wordExplain = sc.nextLine();
            dictionary.add(new Word(wordTarget, wordExplain));
            trie.addWord(wordTarget);
        }
        dictionary.sort(new SortByWord());
    }

    public void insertFromFile(Dictionary dictionary, String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line; //= bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] tmp = line.split("\\|");
                Word word = new Word();
                word.setWordTarget(tmp[0].trim());
                word.setWordExplain(tmp[1].trim());
                dictionary.add(word);
                trie.addWord(word.getWordTarget());
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void dictionaryExportToFile(Dictionary dictionary, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : dictionary) {
                bufferedWriter.write(word.getWordTarget() + "|" + word.getWordExplain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void addWord(Dictionary dictionary, String target, String explain) {
        dictionary.add(new Word(target, explain));
        dictionary.sort(new SortByWord());
        trie.addWord(target);
        //dictionaryExportToFile(dictionary, path);
    }

    public int location(Dictionary dictionary, String key) {
        try {
            int left = 0;
            int right = dictionary.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = dictionary.get(mid).getWordTarget().compareTo(key);
                if (res == 0) return mid;
                if (res <= 0) left = mid + 1;
                else right = mid - 1;
            }
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
        return -1;
    }

    public String dictionaryLookup(Dictionary dictionary, String key) {
        int pos = location(dictionary,key);
        if (pos < 0) return "Not found in dictionary";
        return key + " - " + dictionary.get(pos).getWordExplain();
    }

    public void updateWord(Dictionary dictionary, String key, String updatedExplain) {
        int pos = location(dictionary, key);
        dictionary.get(pos).setWordExplain(updatedExplain);
        //dictionaryExportToFile(dictionary, path);
    }

    public void deleteWord(Dictionary dictionary, String key) {
        int pos = location(dictionary, key);
        dictionary.remove(pos);
        trie.deleteWord(key);
    }

    public String searchWord(String prefix) {
        try {
            List<String> tmp = trie.autoComplete(prefix);
            StringBuilder ans = new StringBuilder();
            for (String s : tmp) {
                ans.append(s).append("\n");
            }
            return ans.toString();
        } catch (NullPointerException e) {
            return "Not found any words or related words.";
        }
    }
}
