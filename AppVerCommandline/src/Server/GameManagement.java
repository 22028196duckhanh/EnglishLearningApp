package Server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManagement {
    private static final int NUMSOFQUESTION = 10;
    private static final int SCOREPERQUESTION = 10;
    private static ArrayList<GameCard> gameCards = new ArrayList<>();
    private static int score = 0;

    private static void init() throws SQLException {
        GameDatabase.init();
        for (int i = 0; i < NUMSOFQUESTION; i++) {
            GameCard card = new GameCard();
            gameCards.add(card);
        }
    }

    private static void close() throws SQLException {
        score = 0;
        gameCards.clear();
        System.out.printf("Your score: %d / %d\n", score, SCOREPERQUESTION*NUMSOFQUESTION);
        GameDatabase.close();
    }

    public static void launch() throws SQLException {
        while (true) {
            init();
            int i = 1;
            for (GameCard card : gameCards) {
                System.out.print(i++ + ".");
                card.showQuestion();
                System.out.print("Enter A, B, C or D: ");
                char c;
                while (true) {
                    c = new Scanner(System.in).next().charAt(0);
                    if (c >= 'A' && c <= 'D') break;
                    else System.out.print("Enter A, B, C or D please: ");
                }
                if (card.checkAnswer(c)) {
                    score += SCOREPERQUESTION;
                    System.out.println("\tCorrect!");
                } else {
                    System.out.println("\tIncorrect!");
                }
            }
            close();
            System.out.println("Do you want to play again? Enter Y to do it again or any key to back to menu:");
            char option = new Scanner(System.in).next().charAt(0);
            if (option != 'Y' && option != 'y') break;
        }
    }
}
