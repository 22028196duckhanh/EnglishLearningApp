package Controller;

import Server.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private Dictionary dictionary = new DatabaseDictionary();
    ObservableList<String> result = FXCollections.observableArrayList();
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
                explanation.setVisible(false);
                speaker.setVisible(false);
            }
        });
        speaker.setOnAction(actionEvent ->  {
            TextToSpeech.playSoundGoogleTranslate(speaker.getText());
        });
        explanation.setVisible(false);
        speaker.setVisible(false);
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
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            explanation.getEngine().loadContent(dictionary.getFullExplain(selectedWord),"text/html");
            speaker.setText(selectedWord);
            History.updateHistory(selectedWord);
            speaker.setVisible(true);
            explanation.setVisible(true);
        }
    }

    private void defaultHistory() {
        result.clear();
        result.addAll(History.getHistory());
        listResults.setItems(result);
    }

    @FXML
    private TextField searchArea;

    @FXML
    private WebView explanation;

    @FXML
    private Button speaker;

    @FXML
    private ListView<String> listResults;
}
