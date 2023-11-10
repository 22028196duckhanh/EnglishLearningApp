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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private Dictionary dictionary = new DatabaseDictionary();
    ObservableList<String> result = FXCollections.observableArrayList();
    private String selectedWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dictionary.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        History.insertFromFile();
        defaultHistory(); 
        searchArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchArea.getText().isEmpty()) {
                    defaultHistory();
                } else {
                    try {
                        handleOnKeyTyped();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                //explanation.setVisible(false);
                speaker.setVisible(false);
            }
        });

        speaker.setOnAction(actionEvent ->  {
            TextToSpeech.playSoundGoogleTranslate(speaker.getText());
        });

        editWord.setOnAction(actionEvent ->  {
            if (explaination.isDisable()) {
                editWord.setText("confirm");
                explaination.setDisable(false);

            } else {
                editWord.setText("edit");
                explaination.setDisable(true);
                try {
                    dictionary.editHtml(selectedWord,explaination.getHtmlText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setDefault.setOnAction(actionEvent ->  {
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
    }

    @FXML
    private void handleOnKeyTyped() throws SQLException {
        result.clear();
        String searchKey = searchArea.getText().trim();
        result.addAll(dictionary.searchWord(searchKey));
        listResults.setItems(result);
    }

    @FXML
    private void handleMouseSelectWord(MouseEvent arg0) throws SQLException {
        selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            //String htmlContent = dictionary.getFullExplain(selectedWord);
            //htmlContent = "<style>body { color: white; }</style>" + htmlContent;
            //explanation.getEngine().loadContent(htmlContent, "text/html");
            String htmlContent = dictionary.getFullExplain(selectedWord);
            explaination.setHtmlText(htmlContent);
            speaker.setText(selectedWord);
            History.updateHistory(selectedWord);
            speaker.setVisible(true);
            explaination.setVisible(true);
            editWord.setVisible(true);
            setDefault.setVisible(true);

            Image speakerImage = new Image("file:src/main/resources/Utils/images/audio.png");

            ImageView speakerIcon = new ImageView(speakerImage);

            speaker.setGraphic(speakerIcon);
        }
    }

    private void defaultHistory() {
        result.clear();
        result.addAll(History.getHistory());
        listResults.setItems(result);
    }

    @FXML
    private TextField searchArea;

    //@FXML
    //private WebView explanation;

    @FXML
    private HTMLEditor explaination;

    @FXML
    private Button editWord;

    @FXML
    private Button setDefault;

    @FXML
    private Button speaker;

    @FXML
    private ListView<String> listResults;
}
