
package Controller;

import Server.CountdownTimer;
import Server.FillGame;
import Server.SoundEffect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class GameFillController implements Initializable {

    @FXML
    private Label questionLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Button checkButton;
    @FXML
    private Label score;
    @FXML
    private Label time;
    private int Score = 0;
    Set<Pair<String, String>> set = new HashSet<>();
    private String correctAnswer = "";

    public GameFillController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws SQLException, ClassNotFoundException {

        score.setVisible(false);
        while (set.size() < 10) {
            set.add(FillGame.getQues());
        }
        Iterator<Pair<String, String>> iterator = set.iterator();
        showQuestion(iterator);
    }

    private void showQuestion(Iterator<Pair<String, String>> iterator) {
        CountdownTimer.gettime(time);
        if (iterator.hasNext()) {
            Pair<String, String> a = iterator.next();
            correctAnswer = a.getValue();
            questionLabel.setText(a.getKey());
            answerField.setText("");
            checkButton.setOnAction(e -> {
                try {
                    checkAnswer();
                    showQuestion(iterator);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } else {
            endGame();
        }
    }
    private void endGame() {
        questionLabel.setVisible(false);
        checkButton.setVisible(false);
        answerField.setVisible(false);
        score.setText("Your score: " + Score);
        time.setVisible(false);
        CountdownTimer.resetTime();
        score.setVisible(true);
    }

    private void checkAnswer() throws SQLException, ClassNotFoundException {
        String userAnswer = answerField.getText().trim();
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            Score += 10;
            SoundEffect.trueSound();
        } else {
            SoundEffect.falseSound();
        }
    }
}