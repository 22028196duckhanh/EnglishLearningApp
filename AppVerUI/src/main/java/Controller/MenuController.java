package Controller;

import Server.History;

import Server.SoundEffect;
import javafx.animation.*;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (History.words.isEmpty()) {
            History.insertFromFile();
        }

        gameSortBtn.setLayoutX(18);
        gameSortBtn.setLayoutY(10);
        gameSortBtn.setPrefSize(335, 200);
        gameSortBtn.setStyle("-fx-opacity: 1");
        gameSortBtn.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                ("/Utils/css/gamebg.css")).toExternalForm());
        gameSortBtn.getStyleClass().add("gameSortBtn");

        gameFillBtn.setLayoutX(562);
        gameFillBtn.setLayoutY(10);
        gameFillBtn.setPrefSize(335, 200);
        gameFillBtn.setStyle("-fx-opacity: 1");
        gameFillBtn.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                ("/Utils/css/gamebg.css")).toExternalForm());
        gameFillBtn.getStyleClass().add("gameFillBtn");

        gameFlipBtn.setLayoutX(18);
        gameFlipBtn.setLayoutY(385);
        gameFlipBtn.setPrefSize(335, 200);
        gameFlipBtn.setStyle("-fx-opacity: 1");
        gameFlipBtn.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                ("/Utils/css/gamebg.css")).toExternalForm());
        gameFlipBtn.getStyleClass().add("gameFlipBtn");

        gameChoiceBtn.setLayoutX(562);
        gameChoiceBtn.setLayoutY(385);
        gameChoiceBtn.setPrefSize(335, 200);
        gameChoiceBtn.setStyle("-fx-opacity: 1");
        gameChoiceBtn.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                ("/Utils/css/gamebg.css")).toExternalForm());
        gameChoiceBtn.getStyleClass().add("gameChoiceBtn");

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/search-view.fxml");
                    searchBtn.setSelected(true);
                    gameBtn.setSelected(false);
                    translateBtn.setSelected(false);
                    highlightBtn.setSelected(false);
                    homeBtn.setSelected(false);

                    fadeOutTransition(btnMode);

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

                fadeOutTransition(btnMode);
                SoundEffect.playSound();
            }
        });

        exitBtn.setOnMouseClicked(e -> {
            History.exportToFile();
            Platform.exit();
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

                    fadeOutTransition(btnMode);
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

                    fadeOutTransition(btnMode);
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
                boxScreen.getChildren().add(handFinger);
                handFinger.setVisible(true);
            }
        });

        handFinger.setOnMouseClicked(e -> {
            handFinger.setDisable(true);
            gameFillBtn.setDisable(true);
            gameSortBtn.setDisable(true);
            gameFlipBtn.setDisable(true);
            gameChoiceBtn.setDisable(true);
            searchBtn.setDisable(true);
            gameBtn.setDisable(true);
            translateBtn.setDisable(true);
            homeBtn.setDisable(true);
            highlightBtn.setDisable(true);
            spin(handFinger);
        });

        homeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boxScreen.getChildren().clear();

                boxScreen.getChildren().add(c1);
                boxScreen.getChildren().add(c2);
                boxScreen.getChildren().add(c3);
                boxScreen.getChildren().add(quote);

                searchBtn.setSelected(false);
                gameBtn.setSelected(false);
                translateBtn.setSelected(false);
                highlightBtn.setSelected(false);

                btnMode.setVisible(true);
                fadeInTransition(btnMode);
            }
        });
        choice = 1;
        handFinger.setVisible(false);
        homeBtn.setSelected(true);
        handleHomeButtonAction();

    }

    public void setView(Node view) {
        boxScreen.getChildren().clear();
        boxScreen.getChildren().add(view);
    }

    @FXML
    public void showView(String path) throws IOException {
        AnchorPane newScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        fadeInTransition(newScreen);
        setView(newScreen);
    }

    public void spin(ImageView imgView) {
        randomGame();
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), imgView);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), imgView);
        int angle = 360 * 3 + (choice - 2) * 90;
        rotateTransition.setByAngle(angle);
        translateTransition.setAutoReverse(true);
        translateImage(translateTransition, imgView, choice);
        rotateTransition.play();
        rotateTransition.setOnFinished(e -> {
            translateTransition.play();
        });
        translateTransition.setOnFinished(e -> {
            try {
                if (choice == 1) {
                    showView("/Views/game-sort-view.fxml");
                } else if (choice == 2) {
                    showView("/Views/game-fill-view.fxml");
                } else if (choice == 4) {
                    showView("/Views/game-flip-view.fxml");
                } else if (choice == 3) {
                    showView("/Views/multiple-choice-view.fxml");
                }
                handFinger.setRotate(0);
                handFinger.setTranslateX(0);
                handFinger.setTranslateY(0);
                createBackBtn();
                boxScreen.getChildren().add(backBtn);
                handFinger.setDisable(false);
                gameFillBtn.setDisable(false);
                gameSortBtn.setDisable(false);
                gameFlipBtn.setDisable(false);
                gameChoiceBtn.setDisable(false);
                searchBtn.setDisable(false);
                gameBtn.setDisable(false);
                translateBtn.setDisable(false);
                homeBtn.setDisable(false);
                highlightBtn.setDisable(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void translateImage(TranslateTransition translateTransition, ImageView imgView, int choice) {
        switch (choice) {
            case 1:
                translateTransition.setToX(imgView.getTranslateX() - 100);
                translateTransition.setToY(imgView.getTranslateY() - 100);
                break;
            case 2:
                translateTransition.setToX(imgView.getTranslateX() + 100);
                translateTransition.setToY(imgView.getTranslateY() - 100);
                break;
            case 3:
                translateTransition.setToX(imgView.getTranslateX() + 100);
                translateTransition.setToY(imgView.getTranslateY() + 100);
                break;
            case 4:
                translateTransition.setToX(imgView.getTranslateX() - 100);
                translateTransition.setToY(imgView.getTranslateY() + 100);
                break;
            default:
                break;
        }
    }

    private void createBackBtn() {
        backBtn.setLayoutX(5);
        backBtn.setLayoutY(5);
        backBtn.setPrefSize(36, 36);
        backBtn.getStyleClass().add("backBtn");
        backBtn.getStylesheets().add("file:src/main/resources/Utils/css/fullpackstyling.css");
    }

    public void randomGame() {
        Random random = new Random();
        choice = Math.abs(random.nextInt()) % 4 + 1;
    }

    @FXML
    private void handleHomeButtonAction() {
        setRotate(c1, true, 360, 10);
        setRotate(c2, true, 180, 18);
        setRotate(c3, true, 145, 24);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), evt -> quote.setVisible(false)),
                new KeyFrame(Duration.seconds(1), evt -> quote.setVisible(true)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
        Image logo = new Image("file:src/main/resources/Utils/images/infinity.png");

        imgMode.setImage(imageMode);
        logoImg.setImage(logo);

        boxScreen.getStyleClass().clear();
        vBox.getStyleClass().clear();
        hBox.getStyleClass().clear();
        label.getStyleClass().clear();

        boxScreen.getStyleClass().add("menu-view");
        vBox.getStyleClass().add("menu-bar");
        hBox.getStyleClass().add("hBar");
        label.getStyleClass().add("label");

        btnMode.setStyle("-fx-background-color: #c2ade6");
    }

    public void setDarkMode() {
        Image imageMode = new Image("file:src/main/resources/Utils/images/icons8-light-mode-48.png");
        Image logo = new Image("file:src/main/resources/Utils/images/infinity2.png");

        imgMode.setImage(imageMode);
        logoImg.setImage(logo);

        boxScreen.getStyleClass().clear();
        hBox.getStyleClass().clear();
        label.getStyleClass().clear();

        boxScreen.getStyleClass().add("menu-view-dark");
        vBox.getStyleClass().add("menu-bar-dark");
        hBox.getStyleClass().add("hBar-dark");
        label.getStyleClass().add("label-dark");

        btnMode.setStyle("-fx-background-color: #ff7e67");
    }

    private void fadeInTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.4), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setOnFinished(event -> node.setVisible(true));
        fadeTransition.play();
    }

    private void fadeOutTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.4), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> node.setVisible(false));
        fadeTransition.play();
    }

    private void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.play();
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

    public static boolean isLightMode = true;

    private int choice;

    @FXML
    private ImageView logoImg;

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

    @FXML
    private Button gameSortBtn = new Button("Game Sort");

    @FXML
    private Button backBtn = new Button("Back");

    @FXML
    private Button gameFillBtn = new Button("Game Fill");

    @FXML
    private Button gameFlipBtn = new Button("Game Flip");

    @FXML
    private Button gameChoiceBtn = new Button("Game Multiple choice");

    @FXML
    private Circle c1;

    @FXML
    private Circle c2;

    @FXML
    private Circle c3;

    @FXML
    private Label quote;
}
