package Controller;

import Server.CountdownTimer;
import Server.FlipGame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class GameFlipController implements Initializable {

    private List<Pair<String, ImageView>> symbols = new ArrayList<>();
    private Button[] buttons = new Button[16];
    private String lastButtonKey = "";
    private Button lastButtonClicked = null;
    ImageView[] imageView = new ImageView[16];
    private String link = "file:src/main/resources/Utils/data/flipgamedata/";
    private boolean[] fliped = new boolean[16];
    @FXML
    GridPane grid;
    @FXML
    Label time;
    @FXML
    ImageView imageview;

    private void buttonClicked(Button button) {
        int buttonIndex = Arrays.asList(buttons).indexOf(button);
        if (!fliped[buttonIndex])
            flipButton1(button, true);
    }

    private void changeGraphic(Button button) {
        int buttonIndex = Arrays.asList(buttons).indexOf(button);
        flipButton(button);
        if (symbols.get(buttonIndex).getValue() != null) {
            button.setGraphic(symbols.get(buttonIndex).getValue());
        } else {
            button.setGraphic(null);
            button.setTextAlignment(TextAlignment.CENTER);
            button.setText(symbols.get(buttonIndex).getKey().substring(0, symbols.get(buttonIndex).getKey().length() - 4).replace('_', ' '));
        }
        fliped[buttonIndex] = true;
        if (lastButtonClicked == null) {
            lastButtonClicked = button;
            lastButtonKey = symbols.get(buttonIndex).getKey();
        } else {
            if (!symbols.get(buttonIndex).getKey().equals(lastButtonKey)) {
                PauseTransition pause = getPauseTransition(button, buttonIndex);
                pause.play();
            } else {
                button.minHeight(150);
                button.minWidth(150);
            }
            lastButtonClicked = null;
            lastButtonKey = "";
        }
    }


    private PauseTransition getPauseTransition(Button button, int buttonIndex) {
        final Button buttonToReset = lastButtonClicked;
        for (int i = 0; i < 16; i++)
            buttons[i].setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(event -> {
            Platform.runLater(() -> {
                flipButton1(button, false);
                flipButton1(buttonToReset, false);
                PauseTransition Pause = new PauseTransition(Duration.millis(200));
                Pause.setOnFinished(e -> {
                    Platform.runLater(() -> {
                        button.setGraphic(imageView[buttonIndex]);
                        buttonToReset.setGraphic(imageView[Arrays.asList(buttons).indexOf(buttonToReset)]);
                        fliped[Arrays.asList(buttons).indexOf(buttonToReset)] = false;
                        fliped[buttonIndex] = false;
                        button.setText("");
                        buttonToReset.setText("");
                        flipButton(button);
                        flipButton(buttonToReset);
                    });
                });
                Pause.play();
                for (int i = 0; i < 16; i++)
                    buttons[i].setDisable(false);
            });
        });
        return pause;
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

    public void flipButton1(Button button, boolean isFront) {

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(0);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition);
        parallelTransition.setCycleCount(1);

        parallelTransition.setOnFinished(event -> {
            if (isFront)
                changeGraphic(button);
        });

        parallelTransition.play();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountdownTimer.resetTime();
        CountdownTimer.gettime(time);
        Set<String> set = new HashSet<>();
        while (set.size() < 8) {
            try {
                set.add(FlipGame.getImage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> list = new ArrayList<>(set);
        for (int i = 0; i < 16; i++) {
            imageView[i] = new ImageView("file:src/main/resources/Utils/images/card.png");
            imageView[i].setFitWidth(105);
            imageView[i].setFitHeight(105);
        }

        for (int i = 0; i < 16; i++) {
            symbols.add(new Pair<>(list.get(i / 2), i % 2 == 0 ? new ImageView(link + list.get(i / 2)) : null));
            if (i % 2 == 0) {
                symbols.get(i).getValue().setFitHeight(105);
                symbols.get(i).getValue().setFitWidth(105);
            }
        }
        Collections.shuffle(symbols);

        for (int i = 0; i < 16; i++) {
            buttons[i] = new Button();
            buttons[i].minHeight(100);
            buttons[i].minWidth(150);
            buttons[i].setGraphic(imageView[i]);
            buttons[i].setWrapText(false);
            buttons[i].setMinSize(150, 150);
            buttons[i].setPrefSize(150, 150);

            buttons[i].setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding: 10;-fx-opacity: 1;");
            int finalI = i;
            buttons[i].setOnAction(e -> buttonClicked(buttons[finalI]));
            grid.add(buttons[i], i % 4, i / 4);
        }
    }
}