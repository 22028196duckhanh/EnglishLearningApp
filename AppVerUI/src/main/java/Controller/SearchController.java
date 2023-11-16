package Controller;

import Server.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.security.PrivateKey;
import java.sql.SQLException;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private DatabaseDictionary dictionary = new DatabaseDictionary();
    ObservableList<String> result = FXCollections.observableArrayList();
    private String selectedWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dictionary.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (History.words.isEmpty()) {
            History.insertFromFile();
        }
        defaultHistory();
        searchArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchArea.getText().isEmpty()) {
                    defaultHistory();
                    explaination.setVisible(false);
                } else {
                    try {
                        handleOnKeyTyped();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                speaker.setVisible(false);
            }
        });

        searchArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                selectedWord = searchArea.getText();
                try {
                    setResults();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        highlight.setOnMouseClicked(e -> {
            try {
                dictionary.setHighlight(selectedWord);
                if (dictionary.getHighlight(selectedWord) == 1) {
                    highlight.setStyle("-fx-background-color: #000000");
                } else highlight.setStyle("-fx-background-color: #FFFFFF");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        speaker.setOnAction(actionEvent -> {
            TextToSpeech.playSoundGoogleTranslate(speaker.getText());
        });

        editWord.setOnAction(actionEvent -> {
            if (explaination.isDisable()) {
                editWord.setText("confirm");
                explaination.setDisable(false);

            } else {
                editWord.setText("edit");
                explaination.setDisable(true);
                try {
                    dictionary.editHtml(selectedWord, explaination.getHtmlText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setDefault.setOnAction(actionEvent -> {
            try {
                dictionary.setDefault(selectedWord);
                String htmlContent = dictionary.getFullExplain(selectedWord);
                explaination.setHtmlText(htmlContent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        //explanation.setVisible(false);

        //String css = "body { background-color: #393351; }";
        //explanation.getEngine().setUserStyleSheetLocation("data:text/css;charset=utf-8," + css);
        Node[] nodes = explaination.lookupAll(".tool-bar").toArray(new Node[0]);
        for (Node node : nodes) {
            node.setVisible(false);
            node.setManaged(false);
        }

        setDefault.setVisible(false);
        editWord.setVisible(false);
        speaker.setVisible(false);
        explaination.setDisable(true);
        explaination.setVisible(false);
        highlight.setVisible(false);
    }

    @FXML
    private void handleOnKeyTyped() throws SQLException {
        result.clear();
        String searchKey = searchArea.getText().trim();
        result.addAll(dictionary.searchWord(searchKey));
        listResults.refresh();
        listResults.setItems(result);
    }

    @FXML
    private void handleSelectWord() throws SQLException {
        selectedWord = listResults.getSelectionModel().getSelectedItem();
        setResults();
    }

    private void setResults() throws SQLException {
        String htmlContent;
        if (selectedWord != null) {
            htmlContent = dictionary.getFullExplain(selectedWord);
            System.out.println(selectedWord);
            if (htmlContent.isEmpty()) {
                explaination.setHtmlText("<h1>No Results</h1>");
            } else {
                explaination.setHtmlText(htmlContent);
            }
            speaker.setText(selectedWord);
            if (!dictionary.getFullExplain(selectedWord).isEmpty()) {
                History.updateHistory(selectedWord);
            }
            if (dictionary.getHighlight(selectedWord) == 1) {
                highlight.setStyle("-fx-background-color: #000000");
            } else highlight.setStyle("-fx-background-color: #FFFFFF");
            speaker.setVisible(true);
            explaination.setVisible(true);
            editWord.setVisible(true);
            setDefault.setVisible(true);
            highlight.setVisible(true);

            Image speakerImage = new Image("file:src/main/resources/Utils/images/audio.png");

            ImageView speakerIcon = new ImageView(speakerImage);

            speaker.setGraphic(speakerIcon);
        }
    }

    @FXML
    public void handleAdd() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setHeaderText("Add a new word");
        DialogPane tmp = dialog.getDialogPane();
        dialog.setHeaderText(null);

        Label newWordLabel = new Label("New word: ");
        TextField newWordField = new TextField();

        Label meaningLabel = new Label("Meaning:");
        TextArea meaningField = new TextArea();
        meaningField.setWrapText(true);

        Label typeLabel = new Label("Type:");
        TextField typeField = new TextField();

        Label pronunciationLabel = new Label("Pronunciation:");
        TextField pronunciationField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.add(newWordLabel, 1, 1);
        gridPane.add(newWordField, 2, 1);
        gridPane.add(meaningLabel, 1, 4);
        gridPane.add(meaningField, 2, 4);
        gridPane.add(typeLabel, 1, 2);
        gridPane.add(typeField, 2, 2);
        gridPane.add(pronunciationLabel, 1, 3);
        gridPane.add(pronunciationField, 2, 3);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

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
                String word = newWordField.getText();
                String meaning = meaningField.getText();
                String type = typeField.getText();
                String pronunciation = pronunciationField.getText();
                try {
                    if (dictionary.addWord(word,pronunciation,type,meaning)) {
                        System.out.println("This word already in dictionary");
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        DialogPane tmp1 = successAlert.getDialogPane();
                        successAlert.setTitle("Success");
                        successAlert.setContentText("Add successfully! This word already in dictionary");
                        successAlert.showAndWait();
                    }
                    else {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        DialogPane tmp1 = successAlert.getDialogPane();
                        successAlert.setTitle("Fail");
                        successAlert.setContentText("Word existed!");
                        successAlert.showAndWait();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                dialog.close();
            }
        });
    }

    private void defaultHistory() {
        result.clear();
        result.addAll(History.getHistory());
        listResults.refresh();
        listResults.setItems(result);
    }

    @FXML
    private TextField searchArea;

    @FXML
    private HTMLEditor explaination;

    @FXML
    private Button addWord;

    @FXML
    private Button editWord;

    @FXML
    private Button highlight;

    @FXML
    private Button setDefault;

    @FXML
    private Button speaker;

    @FXML
    private ListView<String> listResults;
}
