package Controller;

import Server.DatabaseDictionary;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;

public class HighlightController implements Initializable {

    @FXML
    Button word, next, prev, delete, drop;
    @FXML
    Label empty;
    @FXML
    Label wordNumber;
    @FXML
    AnchorPane anchorPane;
    DatabaseDictionary dictionary = new DatabaseDictionary();
    LinkedList<Pair<String, String>> words = new LinkedList<>();
    private String front, back;
    private int count;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        word.setWrapText(true);
        isEmptyScene();

        drop.setOnAction(e -> {
            isEmptyScene();
            Dialog<String> dialog = new Dialog<>();

            dialog.setTitle("drop highlight");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Utils/css/addWord.css")).toExternalForm());
            dialog.setHeaderText(null);

            Label pronunciationLabel = new Label("Do you want to delete all the highlight?");
            pronunciationLabel.setStyle("-fx-font-size: 17;");
            pronunciationLabel.setMinSize(330, 150);
            pronunciationLabel.setMaxSize(320, 150);
            AnchorPane contentPane = new AnchorPane();
            contentPane.getChildren().add(pronunciationLabel);
            AnchorPane.setTopAnchor(pronunciationLabel, 0.0);
            AnchorPane.setBottomAnchor(pronunciationLabel, 0.0);
            AnchorPane.setLeftAnchor(pronunciationLabel, 0.0);
            AnchorPane.setRightAnchor(pronunciationLabel, 0.0);

            dialogPane.setContent(contentPane);

            ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialogPane.getButtonTypes().addAll(okButton, cancelButton);

            ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
            if (buttonBar != null) {
                buttonBar.setButtonMinWidth(Pos.CENTER.ordinal());
            }

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return "OK";
                } else if (dialogButton == cancelButton) {
                    return "Cancel";
                } else {
                    return null;
                }
            });

            dialog.showAndWait().ifPresent(response -> {
                if (response.equals("OK")) {
                    try {
                        dictionary.dropHighlight();
                        words.clear();
                        isEmptyScene();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    dialog.close();
                }
            });

        });

        delete.setOnAction(e -> {
            try {
                if (words.size() == 1) {
                    try {
                        dictionary.dropHighlight();
                        words.clear();
                        isEmptyScene();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (!words.isEmpty()) {
                    words.remove(count);

                    if (count >= words.size()) {
                        count = words.size() - 1;
                    }

                    Pair<String, String> tmp = words.get(count);
                    front = tmp.getKey();
                    back = tmp.getValue();
                    word.setText(front);
                    wordNumber.setText(count + 1 + "/" + words.size());

                    dictionary.deleteHighlight(front);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        next.setOnAction(e -> {
            if (count + 1 < words.size()) {
                count++;
                Pair<String, String> tmp = words.get(count);
                front = tmp.getKey();
                back = tmp.getValue();
                word.setText(front);
                wordNumber.setText(count + 1 + "/" + words.size());
            }
        });

        prev.setOnAction(e -> {
            if (count - 1 >= 0) {
                count--;
                Pair<String, String> tmp = words.get(count);
                front = tmp.getKey();
                back = tmp.getValue();
                word.setText(front);
                wordNumber.setText(count + 1 + "/" + words.size());
            }
        });
        word.setOnAction(actionEvent -> {
            flipButton1(word);
        });
    }

    private void isEmptyScene() {
        try {
            dictionary.searchHighlight(words);
            wordNumber.setText(count + 1 + "/" + words.size());
            empty.setVisible(words.isEmpty());
            word.setVisible(!words.isEmpty());
            next.setVisible(!words.isEmpty());
            prev.setVisible(!words.isEmpty());
            wordNumber.setVisible(!words.isEmpty());

            delete.setVisible(!words.isEmpty());
            drop.setVisible(!words.isEmpty());

            if (!words.isEmpty()) {
                front = words.get(0).getKey();
                back = words.get(0).getValue();
                word.setText(front);
            }
        } catch (SQLException h) {
            throw new RuntimeException(h);
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