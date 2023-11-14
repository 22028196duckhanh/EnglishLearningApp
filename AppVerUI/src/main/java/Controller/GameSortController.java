package Controller;

import Server.Sentence;
import Server.SortDatabase;
import Server.SoundEffect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameSortController implements Initializable {
    private final ArrayList<Sentence> listSentence = new ArrayList<>();
    private int index = 0;
    private int score = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message.setVisible(false);
        scoreText.setText("0");
    }

    public void letGo(ActionEvent event) {
        SortDatabase.getData();
        index = 0;
        score = 0;
        for (int i = 0; i < 10; i++) {
            listSentence.add(SortDatabase.getRdSentence());
        }
        createCards(listSentence.get(index));
        startBtn.setVisible(false);
    }

    public void createCards(Sentence sentence) {

        List<Button> buttonList = new ArrayList<>();
        for (int i = 0; i < sentence.getSize(); i++) {
            Button button = new Button();
            button.setText(sentence.getCmp().get(i));
            button.setPrefSize(150,100);
            buttonList.add(button);
            button.setStyle("-fx-opacity: 1;");
            hBox.getChildren().add(button);
        }

        hBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < sentence.getSize(); i++) {
            int finalI = i;
            buttonList.get(i).setOnAction(actionEvent -> {
                if (buttonList.get(finalI).getParent() == hBox) {
                    answerBox.getChildren().add(buttonList.get(finalI));
                    answerBox.setAlignment(Pos.CENTER);
                    if (answerIsFull(sentence)) {
                        checkAns = new Button();
                        checkAns.setText("Check");
                        checkAns.setStyle("-fx-opacity: 1;"+
                                "-fx-background-color: #4CAF50; "
                                        + "-fx-text-fill: white; "
                                        + "-fx-border-radius: 15; "
                                        + "-fx-background-radius: 15; "
                                        + "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
                        );
                        checkBox.getChildren().add(checkAns);
                        checkAns.setOnAction(event -> {
                            CheckAnswer(sentence);
                            Button next = new Button();
                            next.setText("Next");
                            checkBox.getChildren().add(next);
                            answerBox.setDisable(true);
                            checkAns.setDisable(true);
                            next.setOnAction(e->{
                                answerBox.setDisable(false);
                                checkAns.setDisable(false);
                                answerBox.getChildren().clear();
                                index++;
                                checkBox.getChildren().clear();
                                if (index>=10) {
                                    EndGame();
                                    return;
                                }
                                createCards(listSentence.get(index));
                            });
                            scoreText.setText(Integer.toString(score));
                        });

                    }
                } else {
                    hBox.getChildren().add(buttonList.get(finalI));
                    hBox.setAlignment(Pos.CENTER);
                    checkBox.getChildren().clear();
                }
            });
        }
    }

    private boolean answerIsFull(Sentence sentence) {
        return answerBox.getChildren().size() == sentence.getSize();
    }

    private boolean myCheck(Sentence sentence) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < answerBox.getChildren().size(); i++) {
            if (answerBox.getChildren().get(i) instanceof Button) {
                Button tmp = (Button) (answerBox.getChildren().get(i));
                s.append(tmp.getText());
                if (i < answerBox.getChildren().size()-1) {
                    s.append("/");
                }
            }
        }
        String ans = s.toString().trim();
        System.out.println(ans);
        return sentence.check(ans);
    }

    private void CheckAnswer(Sentence sentence) {
        if (myCheck(sentence)) {
            System.out.println("Correct");
            SoundEffect.trueSound();
            score += 10;
            shakeEffectTrue();

        } else {
            System.out.println("Incorrect");
            SoundEffect.falseSound();
            shakeEffectFalse();
        }
    }

    public void EndGame() {
        startBtn.setVisible(true);
        message.setVisible(true);
        System.out.println("Your score:" + score);
    }

    public void shakeEffectFalse() {
        TranslateTransition shakeTransition = new TranslateTransition(Duration.millis(200), answerBox);
        shakeTransition.setCycleCount(2);
        shakeTransition.setByX(10);
        shakeTransition.setAutoReverse(true);
        shakeTransition.play();
    }

    public void shakeEffectTrue() {
        TranslateTransition shakeTransition = new TranslateTransition(Duration.millis(200), answerBox);
        shakeTransition.setCycleCount(2);
        shakeTransition.setByY(-15);
        shakeTransition.setAutoReverse(true);
        shakeTransition.play();
    }

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox hBox;
    @FXML
    private Button startBtn;
    @FXML
    private HBox answerBox;
    @FXML
    private VBox checkBox;
    @FXML
    private Button checkAns;
    @FXML
    private Label message;
    @FXML
    private Label scoreText;
    @FXML
    private Label time;
}
