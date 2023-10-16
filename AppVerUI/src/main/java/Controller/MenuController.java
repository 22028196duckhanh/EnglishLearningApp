package Controller;

import Server.History;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
// import org.w3c.dom.events.MouseEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showView("/Views/search-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        exitBtn.setOnMouseClicked(e -> {
            History.exportToFile();
            System.exit(0);
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
    }

    @FXML
    private AnchorPane boxScreen;

    @FXML
    private Button searchBtn, translateBtn, gameBtn, exitBtn;

}
