package Server;

import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    public static Dictionary dictionary;

    public static void add() {
        System.out.print("Enter the word you need to add: ");
        String target = new Scanner(System.in).nextLine();
        System.out.print("Enter the meaning of the word: ");
        String mean = new Scanner(System.in).nextLine();
        boolean success = dictionary.insertWord(target, mean);
        if (success) System.out.println("Add successfully.");
        else System.out.println("Word existed");
    }

    public static void remove() {
        System.out.print("Enter the word you need to remove: ");
        String target = new Scanner(System.in).nextLine();
        boolean success = dictionary.deleteWord(target);
        if (success) System.out.println("Remove successfully.");
        else System.out.println("Not existed");
    }

    public static void update() {
        System.out.print("Enter the word you need to update: ");
        String target = new Scanner(System.in).nextLine();
        System.out.print("Enter the meaning of the word: ");
        String updatedMean = new Scanner(System.in).nextLine();
        boolean success = dictionary.modifyWord(target, updatedMean);
        if (success) System.out.println("Update successfully.");
        else System.out.println("Not existed");
    }

    public static void display() {
        System.out.println(dictionary.displayAllWords());
    }

    public static void lookUp() {
        System.out.print("Enter the word you need to look up: ");
        String target = new Scanner(System.in).nextLine();
        System.out.println(dictionary.lookUpWord(target));
    }

    public static void search() {
        System.out.print("Enter the prefix you need to search: ");
        String prefix = new Scanner(System.in).nextLine();
        ArrayList<String> res = new ArrayList<>();
        res = dictionary.searchWord(prefix);
        for (String word : res) {
            System.out.println(word);
        }
    }

    public static void exportToFile() {
        dictionary.exportToFile();
    }

    public void insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of words: ");
        int numOfWord = sc.nextInt();
        System.out.println("Enter " + numOfWord + " word and corresponding meaning");
        String tmp = sc.nextLine();
        for (int i = 0; i < numOfWord; i++) {
            String wordTarget = sc.nextLine();
            String wordExplain = sc.nextLine();
            dictionary.insertWord(wordTarget, wordExplain);
        }
    }

    public static void exit() {
        dictionary.close();
        System.exit(0);
    }
}
