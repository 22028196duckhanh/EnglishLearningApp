package Controller;

import Server.Sentence;
import Server.SortDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
            button.setMaxHeight(hBox.getMaxHeight());
            buttonList.add(button);
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
                        checkBox.getChildren().add(checkAns);
                        checkAns.setOnAction(event -> {
                            CheckAnswer(sentence);
                            answerBox.getChildren().clear();
                            index++;
                            checkBox.getChildren().clear();
                            if (index>=10) {
                                EndGame();
                                return;
                            }
                            createCards(listSentence.get(index));

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
                s.append(tmp.getText()).append(" ");
            }
        }
        String ans = s.toString().trim();
        System.out.println(ans);
        return sentence.check(ans);
    }

    private  void CheckAnswer(Sentence sentence) {
        if (myCheck(sentence)) {
            System.out.println("Correct");
            score += 10;
        } else {
            System.out.println("Incorrect");
        }
    }

    public void EndGame() {
        startBtn.setVisible(true);
        message.setVisible(true);
        System.out.println("Your score:" + score);
    }

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
    private Label time;
}
