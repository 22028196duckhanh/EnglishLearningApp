
package Controller;

import Server.FlipGame;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class GameFlipController implements Initializable {

    private List<Pair<String,ImageView>> symbols = new ArrayList<>();
    private Button[] buttons = new Button[16];
    private String lastButtonKey = "";
    private Button lastButtonClicked = null;
    ImageView[] imageView = new ImageView[16];
    private String link = "file:src/main/resources/Utils/data/flipgamedata/";
    private boolean[] fliped = new boolean[16];
    @FXML
    GridPane grid;

    private void buttonClicked(Button button, ImageView[] imageView) {
        int buttonIndex = Arrays.asList(buttons).indexOf(button);
        if (!fliped[buttonIndex]) {
            if(symbols.get(buttonIndex).getValue() != null){
            button.setGraphic(symbols.get(buttonIndex).getValue());
            }
            else {
                button.setGraphic(null);
                button.setTextAlignment(TextAlignment.CENTER);
                button.setText(symbols.get(buttonIndex).getKey().substring(0, symbols.get(buttonIndex).getKey().length() - 4).replace('_',' '));
            }
            fliped[buttonIndex] = true;
            if (lastButtonClicked == null) {
                lastButtonClicked = button;
                lastButtonKey = symbols.get(buttonIndex).getKey();
            } else {
                if (!symbols.get(buttonIndex).getKey().equals(lastButtonKey)) {
                    PauseTransition pause = getPauseTransition(button, imageView, buttonIndex);
                    pause.play();
                }
                else {

                    button.minHeight(150);
                    button.minWidth(150);
                }
                lastButtonClicked = null;
                lastButtonKey = "";
            }
        }
    }

    private PauseTransition getPauseTransition(Button button, ImageView[] imageView, int buttonIndex) {
        final Button buttonToReset = lastButtonClicked;

        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(event -> {
            Platform.runLater(() -> {
                button.setGraphic(imageView[buttonIndex]);
                buttonToReset.setGraphic(imageView[Arrays.asList(buttons).indexOf(buttonToReset)]);
                fliped[Arrays.asList(buttons).indexOf(buttonToReset)] = false;
                button.setText("");
                buttonToReset.setText("");
                fliped[buttonIndex] = false;
            });
        });
        return pause;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Set<String> set = new HashSet<>();
        while(set.size() < 8) {
            try {
                set.add(FlipGame.getImage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> list = new ArrayList<>(set);
        for(int i = 0; i< 16; i++){
            imageView[i] = new ImageView("file:src/main/resources/Utils/images/audio.png");
            imageView[i].setFitWidth(150);
            imageView[i].setFitHeight(150);
        }

        for (int i = 0; i < 16; i++) {
            symbols.add(new Pair<>(list.get(i/2), i % 2 == 0 ? new ImageView(link + list.get(i/2)) : null));
            if(i % 2 == 0){
                symbols.get(i).getValue().setFitHeight(150);
                symbols.get(i).getValue().setFitWidth(150);
            }
        }
        Collections.shuffle(symbols);

        for (int i = 0; i < 16; i++) {
            buttons[i] = new Button();
            buttons[i].minHeight(100);
            buttons[i].minWidth(150);
            buttons[i].setGraphic(imageView[i]);
            buttons[i].setWrapText(false);
            buttons[i].setMinSize(150,150);
            buttons[i].setPrefSize(150,150);

            buttons[i].setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding: 10;");

            int finalI = i;
            buttons[i].setOnAction(e -> buttonClicked(buttons[finalI], imageView));

            grid.add(buttons[i], i % 4, i / 4);
        }

    }
}