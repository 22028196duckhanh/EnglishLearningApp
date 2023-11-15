package Server;

import java.io.*;
import java.util.*;

public class History {
    private static final int MAX_HISTORY_WORDS = 7;
    private static final String path = "src/main/resources/Utils/data/history.txt";
    public static ArrayList <String> words = new ArrayList<>();

    public static void insertFromFile() {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                updateHistory(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public static void updateHistory(String newWord) {
        words.remove(newWord);
        words.add(newWord);
        while (words.size() > MAX_HISTORY_WORDS) {
            words.remove(0);
        }
    }

    public static void exportToFile() {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String word : words) {
                bufferedWriter.write(word);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public static ArrayList<String> getHistory() {
        ArrayList<String> his = new ArrayList<>(words);
        Collections.reverse(his);
        return his;
    }
}

