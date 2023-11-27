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
import javafx.scene.control.Label;
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
    Label empty;
    @FXML
    AnchorPane anchorPane;
    DatabaseDictionary dictionary = new DatabaseDictionary();
    LinkedList<Pair<String, String>> words = new LinkedList<>();
    private String front, back;
    private ListIterator<Pair<String,String>> iterator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        word.setWrapText(true);

        try {
            dictionary.searchHighlight(words);
            empty.setVisible(words.isEmpty());
            word.setVisible(!words.isEmpty());
            next.setVisible(!words.isEmpty());
            prev.setVisible(!words.isEmpty());
            iterator = words.listIterator();

            if(!words.isEmpty()) {
                front = words.get(0).getKey();
                back = words.get(0).getValue();
                word.setText(front);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        next.setOnAction(e -> {
            if(iterator.hasNext()){
                Pair<String,String> tmp = null;
                while(iterator.hasNext() && (tmp == null || tmp.getKey().equals(front))) {
                    tmp = iterator.next();
                }
                assert tmp != null;
                front = tmp.getKey();
                back = tmp.getValue();
                word.setText(front);
            }
        });

        prev.setOnAction(e -> {
            if(iterator.hasPrevious()){
                Pair<String,String> tmp = null;
                while(iterator.hasPrevious() && (tmp == null || tmp.getKey().equals(front))) {
                    tmp = iterator.previous();
                }
                assert tmp != null;
                front = tmp.getKey();
                back = tmp.getValue();
                word.setText(front);
            }
        });
        word.setOnAction(actionEvent -> {
            flipButton1(word);
        });
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
