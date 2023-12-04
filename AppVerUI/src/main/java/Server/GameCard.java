package Server;

import java.sql.SQLException;
import java.util.Map;

public class GameCard {
    private final Map<String, Boolean> answer;

    GameCard() throws SQLException {
        int idQuestion = GameDatabase.getIdQuestion();
        this.answer = GameDatabase.getAnswer(idQuestion);
    }

    public void showQuestion() {
        int i = 0;
        for (Map.Entry<String, Boolean> tmp : answer.entrySet()) {
            i++;
        }
    }

    public boolean checkAnswer(char c) {
        int option = c - 'A';
        int i = 0;
        for (Map.Entry<String, Boolean> tmp : answer.entrySet()) {
            if (i == option) return tmp.getValue();
            i++;
        }
        return false;
    }
}
