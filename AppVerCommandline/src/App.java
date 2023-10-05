import Server.Dictionary;
import Server.DictionaryManagement;
import Server.MyNoteDictionary;

import javax.swing.tree.DefaultTreeCellEditor;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static int displayOptions() {
        while (true) {
            System.out.println("""
                Choose an option to execute:
                [0] Exit
                [1] Add
                [2] Remove
                [3] Update
                [4] Display
                [5] Lookup
                [6] Search
                [7] Game
                [8] Export to file""");
            System.out.print("==> Enter an option (0-8): ");
            try {
                int option = new Scanner(System.in).nextInt();
                if (0 <= option && option <= 8) {
                    return option;
                } else {
                    System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter number in (0-8) please!");
            }
        }
    }

    public static void executeSelection(int option) {
        switch (option) {
            case 0 -> DictionaryManagement.exit();
            case 1 -> DictionaryManagement.add();
            case 2 -> DictionaryManagement.remove();
            case 3 -> DictionaryManagement.update();
            case 4 -> DictionaryManagement.display();
            case 5 -> DictionaryManagement.lookUp();
            case 6 -> DictionaryManagement.search();
            case 7 -> DictionaryManagement.exit();
            case 8 -> DictionaryManagement.exportToFile();
            default -> System.out.println("Invalid option!");
        }
    }

    public static void main(String[] args) {
        DictionaryManagement.dictionary = new MyNoteDictionary();
        DictionaryManagement.dictionary.init();
        int option;
        do {
            option = displayOptions();
            executeSelection(option);
        } while (option != 0);
        DictionaryManagement.exit();
    }
}