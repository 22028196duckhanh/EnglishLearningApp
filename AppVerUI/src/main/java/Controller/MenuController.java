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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        gameFillBtn.resizeRelocate(710, 100, 100, 100);
        gameFillBtn.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: linear-gradient(to bottom, #5267f8, rgba(65, 225, 212, 0.87));"
        );

        gameChoiceBtn.resizeRelocate(680, 500, 100, 100);
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
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/search-view.fxml");

                    gameBtn.setSelected(false);
                    translateBtn.setSelected(false);
                    highlightBtn.setSelected(false);
                    homeBtn.setSelected(false);
                    btnMode.setVisible(false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

                searchBtn.setSelected(false);
                translateBtn.setSelected(false);
                highlightBtn.setSelected(false);
                homeBtn.setSelected(false);

                btnMode.setVisible(true);
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
                createBackBtn();
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
                createBackBtn();
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
                createBackBtn();
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
                createBackBtn();
                boxScreen.getChildren().add(backBtn);
            }
        });
        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/translate-view.fxml");

                    searchBtn.setSelected(false);
                    gameBtn.setSelected(false);
                    highlightBtn.setSelected(false);
                    homeBtn.setSelected(false);
                    btnMode.setVisible(false);
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

                    searchBtn.setSelected(false);
                    gameBtn.setSelected(false);
                    translateBtn.setSelected(false);
                    homeBtn.setSelected(false);

                    btnMode.setVisible(true);
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
        homeBtn.setSelected(true);

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boxScreen.getChildren().clear();

                searchBtn.setSelected(false);
                gameBtn.setSelected(false);
                translateBtn.setSelected(false);
                highlightBtn.setSelected(false);

                btnMode.setVisible(true);
            }
        });
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
                createBackBtn();
                boxScreen.getChildren().add(backBtn);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void createBackBtn() {
        backBtn.setLayoutX(5);
        backBtn.setLayoutY(5);
        backBtn.setPrefSize(36, 36);
        backBtn.getStyleClass().add("backBtn");
        backBtn.getStylesheets().add("file:src/main/resources/Utils/css/fullpackstyling.css");
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

    public void changeMode(ActionEvent event) {
        isLightMode = !isLightMode;
        if (btnMode.isSelected()) {
            setDarkMode();
        } else {
            setLightMode();
        }
    }

    public void setLightMode() {
        Image imageMode = new Image("file:src/main/resources/Utils/images/icons8-night-48.png");
        imgMode.setImage(imageMode);

        boxScreen.getStyleClass().clear();
        boxScreen.getStyleClass().add("menu-view");

        vBox.getStyleClass().clear();
        vBox.getStyleClass().add("menu-bar");

        btnMode.setStyle("-fx-background-color: #c2ade6");

        hBox.getStyleClass().clear();
        hBox.getStyleClass().add("hBar");

        label.getStyleClass().clear();
        label.getStyleClass().add("label");
    }

    public void setDarkMode () {
        Image imageMode = new Image("file:src/main/resources/Utils/images/icons8-light-mode-48.png");
        imgMode.setImage(imageMode);

        boxScreen.getStyleClass().clear();
        boxScreen.getStyleClass().add("menu-view-dark");


        vBox.getStyleClass().add("menu-bar-dark");

        btnMode.setStyle("-fx-background-color: #fcff82");

        hBox.getStyleClass().clear();
        hBox.getStyleClass().add("hBar-dark");

        label.getStyleClass().clear();
        label.getStyleClass().add("label-dark");
    }

    public static boolean isLightMode = true;

    private int choice;
    private int storage;

    @FXML
    private ImageView imgMode;

    @FXML
    private HBox hBox;

    @FXML
    private VBox vBox;

    @FXML
    private Label label;

    @FXML
    private ToggleButton btnMode;

    @FXML
    public AnchorPane boxScreen;

    @FXML
    private ToggleButton homeBtn, searchBtn, translateBtn, gameBtn, highlightBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private ImageView handFinger;

    private Button gameSortBtn = new Button("Game Sort");

    private Button backBtn = new Button("Back");

    private Button gameFillBtn = new Button("Game Fill");

    private Button gameFlipBtn = new Button("Game Flip");

    private Button gameChoiceBtn = new Button("Game Multiple choice");
}
