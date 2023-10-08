package Server;

import java.sql.SQLException;
import java.util.Map;

public class GameCard {
    private int idQuestion;
    private String question;
    private Map<String, Boolean> answer;

    GameCard() throws SQLException {
        this.idQuestion = GameDatabase.getIdQuestion();
        this.question = GameDatabase.getQuestion(this.idQuestion);
        this.answer = GameDatabase.getAnswer(this.idQuestion);
    }

    public void showQuestion() {
        System.out.println(this.question);
        int i = 0;
        for (Map.Entry<String, Boolean> tmp : answer.entrySet()) {
            System.out.println((char)('A'+i) + "." + tmp.getKey());
            i++;
        }
    }

    public boolean checkAnswer(char c) {
        int option = c - 'A';
        int i = 0;
        for (Map.Entry<String, Boolean> tmp : answer.entrySet()) {
            if(i == option) return tmp.getValue();
            i++;
        }
        return false;
    }
}
