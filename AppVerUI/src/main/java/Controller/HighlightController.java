package Controller;

import Server.DatabaseDictionary;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class HighlightController implements Initializable {

    @FXML
    Button word, next, prev;

    @FXML
    AnchorPane anchorPane;
    DatabaseDictionary dictionary = new DatabaseDictionary();
    LinkedList<Pair<String, String>> words = new LinkedList<>();
    private String front, back;
    private ListIterator<Pair<String,String>> iterator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image backgroundImage = new Image("file:src/main/resources/Utils/images/fillgame_background.jpg");
        word.setWrapText(true);
        javafx.scene.layout.BackgroundImage background = new javafx.scene.layout.BackgroundImage(
                backgroundImage,
                javafx.scene.layout.BackgroundRepeat.NO_REPEAT,
                javafx.scene.layout.BackgroundRepeat.NO_REPEAT,
                javafx.scene.layout.BackgroundPosition.DEFAULT,
                javafx.scene.layout.BackgroundSize.DEFAULT
        );

        javafx.scene.layout.Background backgroundObject = new javafx.scene.layout.Background(background);

        anchorPane.setBackground(backgroundObject);

        try {
            if(dictionary == null)
                dictionary.init();
            dictionary.searchHighlight(words);
            iterator = words.listIterator();
            front = words.get(0).getKey();
            back = words.get(0).getValue();
            word.setText(front);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*
        next.setOnAction(e -> {
            if(iterator.hasNext()){
                Pair<String,String> tmp= iterator.next();
                front = tmp.getKey();
                back = tmp.getValue();
                word.setText(front);
            }
        });

        prev.setOnAction(e -> {
            if(iterator.hasPrevious()){
                Pair<String,String> tmp= iterator.previous();
                front = tmp.getKey();
                back = tmp.getValue();
                word.setText(front);
            }
        });
        */
        word.setOnAction(actionEvent -> {
            flipButton1(word);
        });
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {
        if(iterator.hasNext()){
            Pair<String,String> tmp= iterator.next();
            front = tmp.getKey();
            back = tmp.getValue();
            word.setText(front);
        }
    }

    @FXML
    void prevButtonOnAction(ActionEvent event) {
        if(iterator.hasPrevious()){
            Pair<String,String> tmp= iterator.previous();
            front = tmp.getKey();
            back = tmp.getValue();
            word.setText(front);
        }
    }

    public static void flipButton(Button button) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.2), button);
        rotateTransition.setAxis(Rotate.Y_AXIS);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(0);
        scaleTransition.setToX(1);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, rotateTransition);
        parallelTransition.setCycleCount(1);

        parallelTransition.setOnFinished(event -> {
            button.setScaleX(1);
        });

        parallelTransition.play();
    }

    public void flipButton1(Button button) {

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(0);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition);
        parallelTransition.setCycleCount(1);

        parallelTransition.setOnFinished(event -> {
            word.setText(word.getText().equals(front) ? back : front);
            flipButton(word);
        });

        parallelTransition.play();
    }
}
