package Controller;

import Server.DatabaseDictionary;
import Server.Dictionary;
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
        searchArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchArea.getText().isEmpty()) {

                } else {
                    try {
                        handleOnKeyTyped();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                explanation.setVisible(false);
            }
        });
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
            explanation.setVisible(true);
        }
    }

    @FXML
    private TextField searchArea;

    @FXML
    private WebView explanation;

    @FXML
    private ListView<String> listResults;
}
