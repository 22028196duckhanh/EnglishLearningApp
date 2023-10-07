import Server.DatabaseDictionary;
import Server.Dictionary;
import Server.DictionaryManagement;
import Server.MyNoteDictionary;

import javax.swing.tree.DefaultTreeCellEditor;
import java.io.IOException;
import java.sql.SQLException;
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
                [8] Export to file
                [9] Google Translator""");
            System.out.print("==> Enter an option (0-9): ");
            try {
                int option = new Scanner(System.in).nextInt();
                if (0 <= option && option <= 9) {
                    return option;
                } else {
                    System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter number in (0-99) please!");
            }
        }
    }

    public static void executeSelection(int option) throws SQLException, IOException {
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
            case 9 -> DictionaryManagement.translator();
            default -> System.out.println("Invalid option!");
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        DictionaryManagement.dictionary = new DatabaseDictionary();
        DictionaryManagement.dictionary.init();
        int option;
        do {
            option = displayOptions();
            executeSelection(option);
        } while (option != 0);
        DictionaryManagement.exit();
    }
}