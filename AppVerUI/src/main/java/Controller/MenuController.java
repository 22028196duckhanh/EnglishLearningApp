package Controller;

import Server.History;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
// import org.w3c.dom.events.MouseEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameSortBtn.resizeRelocate(100, 100, 100, 100);
        gameSortBtn.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
        );
        gameFillBtn.resizeRelocate(500, 100, 100, 100);
        gameFillBtn.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
        );

        gameChoiceBtn.resizeRelocate(500, 500, 100, 100);
        gameChoiceBtn.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
        );

        gameFlipBtn.resizeRelocate(100, 500, 100, 100);
        gameFlipBtn.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
        );
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/search-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                handFinger.setVisible(false);
            }
        });

        gameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boxScreen.getChildren().clear();
                boxScreen.getChildren().add(gameSortBtn);
                boxScreen.getChildren().add(gameFillBtn);
                boxScreen.getChildren().add(gameFlipBtn);
                boxScreen.getChildren().add(gameChoiceBtn);
                boxScreen.getChildren().add(handFinger);
                handFinger.setVisible(true);
            }
        });

        exitBtn.setOnMouseClicked(e -> {
            History.exportToFile();
            System.exit(0);
        });

        gameSortBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/game-sort-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                backBtn.resizeRelocate(100, 500, 100, 100);
                backBtn.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-cursor: hand;" +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
                );
                boxScreen.getChildren().add(backBtn);
                handFinger.setVisible(false);
            }
        });

        gameFillBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/game-fill-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                backBtn.resizeRelocate(100, 500, 100, 100);
                backBtn.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
                );
                boxScreen.getChildren().add(backBtn);
                handFinger.setVisible(false);
            }
        });

        gameChoiceBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/multiple-choice-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                backBtn.resizeRelocate(100, 500, 100, 100);
                backBtn.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
                );
                boxScreen.getChildren().add(backBtn);
                handFinger.setVisible(false);
            }
        });

        gameFlipBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/game-flip-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                backBtn.resizeRelocate(100, 500, 100, 100);
                backBtn.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
                );
                boxScreen.getChildren().add(backBtn);
            }
        });
        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/translate-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        highlightBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/highlight-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boxScreen.getChildren().clear();
                boxScreen.getChildren().add(gameSortBtn);
                boxScreen.getChildren().add(gameFillBtn);
                boxScreen.getChildren().add(gameFlipBtn);
                boxScreen.getChildren().add(gameChoiceBtn);
                handFinger.setRotate(0);
                boxScreen.getChildren().add(handFinger);
                handFinger.setVisible(true);
            }
        });
        handFinger.setOnMouseClicked(e-> {
            spin(handFinger);
        });
        choice = 1;
        storage = 0;
        handFinger.setVisible(false);
    }

    public void setView(Node view) {
        boxScreen.getChildren().clear();
        boxScreen.getChildren().add(view);
    }

    @FXML
    public void showView(String path) throws IOException {
        AnchorPane newScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        setView(newScreen);
    }

    public void spin(ImageView imgView) {
        randomGame();
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), imgView);
        int angle = 360 * 3 + (choice-2) * 90;
        rotateTransition.setByAngle(angle);
        rotateTransition.play();
        rotateTransition.setOnFinished(e -> {
            try {
                if (choice == 1) {
                    showView("/Views/game-sort-view.fxml");
                } else if (choice == 2) {
                    showView("/Views/game-fill-view.fxml");
                } else if (choice == 4) {
                    showView("/Views/game-flip-view.fxml");
                } else if (choice == 3){
                    showView("/Views/multiple-choice-view.fxml");
                }
                backBtn.resizeRelocate(100, 500, 100, 100);
                backBtn.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87))");                boxScreen.getChildren().add(backBtn);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void min(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void max(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.setFullScreen(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
        History.exportToFile();
        System.exit(0);
    }

    public void randomGame() {
        Random random = new Random();
        choice = Math.abs(random.nextInt()) % 4 + 1;
    }
    private int choice;
    private int storage;
    @FXML
    public AnchorPane boxScreen;

    @FXML
    private Button searchBtn, translateBtn, gameBtn, exitBtn, highlightBtn;

    @FXML
    private ImageView handFinger;

    private Button gameSortBtn = new Button("Game Sort");

    private Button backBtn = new Button("Back");

    private Button gameFillBtn = new Button("Game Fill");

    private Button gameFlipBtn = new Button("Game Flip");

    private Button gameChoiceBtn = new Button("Game Multiple choice");
}
