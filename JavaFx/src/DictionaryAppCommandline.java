//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Scanner;

public class DictionaryAppCommandline {
    DictionaryManagement dictionaryManagement = new DictionaryManagement();
    DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
    Dictionary dictionary = new Dictionary();
    private final String path = "src/Resource/dictionary.txt";

    public DictionaryAppCommandline() {
    }

    public void dictionaryBasic() {
        this.dictionaryManagement.insertFromCommandline(this.dictionary);
        this.dictionaryCommandline.showAllWord(this.dictionary);
    }

    public void dictionaryAdvanced() {
        System.out.println("Welcome to My Application!\n[0] Exit\n[1] Add\n[2] Remove\n[3] Update\n[4] Display\n[5] Lookup\n[6] Search\n[7] Game\n[8] Import from file\n[9] Export to file\nYour action:");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice != 0) {
            if (choice == 1) {
                this.addd();
            } else if (choice == 2) {
                this.removee();
            } else if (choice == 3) {
                this.updatee();
            } else if (choice == 4) {
                this.displayy();
            } else if (choice == 5) {
                this.lookupp();
            } else if (choice == 6) {
                this.searchh();
            } else if (choice == 7) {
                this.gamee();
            } else if (choice == 8) {
                this.importFilee();
            } else if (choice == 9) {
                this.exportFilee();
            } else {
                System.out.println("Action not supported");
            }

            this.dictionaryAdvanced();
        }
    }

    private void displayy() {
        this.dictionaryCommandline.showAllWord(this.dictionary);
    }

    private void updatee() {
        System.out.print("Enter the word you need to update: ");
        String tmp1 = (new Scanner(System.in)).nextLine();
        System.out.print("Enter the meaning of the word: ");
        String tmp2 = (new Scanner(System.in)).nextLine();
        this.dictionaryManagement.updateWord(this.dictionary, tmp1, tmp2);
        System.out.println("Update successfully!");
    }

    private void addd() {
        System.out.print("Enter the word you need to add: ");
        String tmp1 = (new Scanner(System.in)).nextLine();
        System.out.print("Enter the meaning of the word: ");
        String tmp2 = (new Scanner(System.in)).nextLine();
        this.dictionaryManagement.addWord(this.dictionary, tmp1, tmp2);
        System.out.println("Add successfully!");
    }

    private void removee() {
        System.out.print("Enter the word you need to remove: ");
        String tmp = (new Scanner(System.in)).nextLine();
        this.dictionaryManagement.deleteWord(this.dictionary, tmp);
        System.out.println("Remove successfully!");
    }

    private void lookupp() {
        System.out.print("Enter the word you need to look up: ");
        String tmp = (new Scanner(System.in)).nextLine();
        System.out.println(this.dictionaryManagement.dictionaryLookup(this.dictionary, tmp));
    }

    private void searchh() {
        System.out.print("Enter the word you need to search: ");
        String tmp = (new Scanner(System.in)).nextLine();
        System.out.println(this.dictionaryManagement.searchWord(tmp));
    }

    private void gamee() {
    }

    private void importFilee() {
        this.dictionaryManagement.insertFromFile(this.dictionary, "src/Resource/dictionary.txt");
    }

    private void exportFilee() {
        this.dictionaryManagement.dictionaryExportToFile(this.dictionary, "src/Resource/dictionary.txt");
    }
}
