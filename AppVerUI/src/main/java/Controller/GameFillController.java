
package Controller;

import Server.FillGame;
import Server.SoundEffect;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class GameFillController extends Game implements Initializable {

    @FXML
    private Label questionLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField answerField;
    @FXML
    private Button checkButton;
    @FXML
    private Label score;
    @FXML
    private Label finalScore;
    @FXML
    private Label time;
    @FXML
    private Label restartLabel;
    @FXML
    private Button restart;
    @FXML
    private ImageView imageview;
    Set<Pair<String, String>> set = new HashSet<>();
    private String correctAnswer = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        restart.setOnAction(e -> {
            try {
                set.clear();
                super.score = 0;
                start();
            } catch (SQLException | ClassNotFoundException h) {
                throw new RuntimeException(h);
            }
        });
        restart.fire();
    }

    public void start() throws SQLException, ClassNotFoundException {
        super.stopTimeline();
        answerField.setVisible(true);
        questionLabel.setVisible(true);
        time.setVisible(true);
        score.setVisible(true);
        checkButton.setVisible(true);
        restart.setVisible(false);
        restartLabel.setVisible(false);
        getTime(time);

        finalScore.setVisible(false);
        while (set.size() < 10) {
            set.add(FillGame.getQues());
        }
        Iterator<Pair<String, String>> iterator = set.iterator();
        showQuestion(iterator);
        answerField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    checkAnswer();
                    showQuestion(iterator);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void showQuestion(Iterator<Pair<String, String>> iterator) {

        if (iterator.hasNext()) {
            Pair<String, String> a = iterator.next();
            score.setText("Score: " + super.score);
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
            end();
        }
    }

    @Override
    public void end() {
        SoundEffect.endSound();
        imageview.setImage(new Image("file:src/main/resources/Utils/images/congratulation.gif"));
        imageview.setBlendMode(BlendMode.MULTIPLY);
        finalScore.setVisible(true);
        finalScore.setText("Your score: " + super.score);
        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.play();
        pause.setOnFinished(e -> {
            imageview.setVisible(false);
        });
        questionLabel.setVisible(false);
        checkButton.setVisible(false);
        answerField.setVisible(false);
        time.setVisible(false);
        score.setVisible(false);
        restart.setVisible(true);
        restartLabel.setVisible(true);
    }

    private void checkAnswer() throws SQLException, ClassNotFoundException {
        String userAnswer = answerField.getText().trim();
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            SoundEffect.trueSound();
            super.setScore();
        } else {
            SoundEffect.falseSound();
        }
    }
}