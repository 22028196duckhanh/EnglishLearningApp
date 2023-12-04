package Controller;

import Server.Sentence;
import Server.SortDatabase;
import Server.SoundEffect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameSortController extends Game implements Initializable {
    private final ArrayList<Sentence> listSentence = new ArrayList<>();
    private int index = 0;
    private int score = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message.setVisible(false);
        check.setImage(new Image("file:src/main/resources/Utils/images/check_sort.png"));
        check.setOnMouseEntered(e -> {
            check.setImage(new Image("file:src/main/resources/Utils/images/check_sort_light.png"));
        });
        check.setOnMouseExited(e -> {
            check.setImage(new Image("file:src/main/resources/Utils/images/check_sort.png"));
        });
        next.setImage(new Image("file:src/main/resources/Utils/images/icons8-next-64.png"));
        next.setOnMouseEntered(e -> {
            next.setImage(new Image("file:src/main/resources/Utils/images/icons8-next-64-2.png"));
        });
        next.setOnMouseExited(e -> {
            next.setImage(new Image("file:src/main/resources/Utils/images/icons8-next-64.png"));
        });
        start.setImage(new Image("file:src/main/resources/Utils/images/start_white.png"));
        check.setVisible(false);
        next.setVisible(false);
        scoreText.setVisible(false);
        endScore.setVisible(false);
        fans.setVisible(false);
    }

    public void letGo(MouseEvent event) {
        SortDatabase.getData();
        index = 0;
        score = 0;
        answerBox.getChildren().clear();
        endScore.setVisible(false);
        message.setVisible(false);
        hBox.setVisible(true);
        answerBox.setVisible(true);
        fans.setVisible(true);
        scoreText.setImage(new Image("file:src/main/resources/Utils/images/score" + score + ".png"));
        start.setVisible(false);
        scoreText.setVisible(true);
        for (int i = 0; i < 10; i++) {
            listSentence.add(SortDatabase.getRdSentence());
        }
        createCards(listSentence.get(index));
    }

    public void createCards(Sentence sentence) {

        List<Button> buttonList = new ArrayList<>();
        for (int i = 0; i < sentence.getSize(); i++) {
            Button button = new Button();
            button.setText(sentence.getCmp().get(i));
            button.setPrefSize(150, 100);
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
                        check.setVisible(true);
                        check.setDisable(false);
                        check.setOnMouseClicked(event -> {
                            CheckAnswer(sentence);
                            scoreText.setImage(new Image("file:src/main/resources/Utils/images/score" + score + ".png"));
                            next.setVisible(true);
                            answerBox.setDisable(true);
                            check.setDisable(true);
                            next.setOnMouseClicked(e -> {
                                answerBox.setDisable(false);
                                check.setVisible(false);
                                answerBox.getChildren().clear();
                                index++;
                                next.setVisible(false);
                                createCards(listSentence.get(index));
                            });
                            if (index >= 9) {
                                end();
                            }
                        });
                    }
                } else {
                    hBox.getChildren().add(buttonList.get(finalI));
                    hBox.setAlignment(Pos.CENTER);
                    check.setVisible(false);
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
                if (i < answerBox.getChildren().size() - 1) {
                    s.append("/");
                }
            }
        }
        String ans = s.toString().trim();
        return sentence.check(ans);
    }

    private void CheckAnswer(Sentence sentence) {
        if (myCheck(sentence)) {
            SoundEffect.trueSound();
            score += 10;
            shakeEffectTrue();

        } else {
            SoundEffect.falseSound();
            shakeEffectFalse();
        }
    }

    @Override
    public void end() {
        SoundEffect.endSound();
        endScore.setImage(new Image("file:src/main/resources/Utils/images/score" + score + ".png"));
        endScore.setVisible(true);
        start.setVisible(true);
        message.setVisible(true);
        hBox.setVisible(false);
        answerBox.setVisible(false);
        check.setVisible(false);
        next.setVisible(false);
        scoreText.setVisible(false);
        fans.setVisible(false);
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
    private AnchorPane bkgrSort;
    @FXML
    private HBox hBox;
    @FXML
    private ImageView start;
    @FXML
    private HBox answerBox;
    @FXML
    private ImageView endScore;

    @FXML
    private ImageView check;

    @FXML
    private ImageView next;

    @FXML
    private ImageView message;

    @FXML
    private ImageView scoreText;

    @FXML
    private Pane fans;
}
