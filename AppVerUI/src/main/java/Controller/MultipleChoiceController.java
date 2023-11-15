package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class MultipleChoiceController implements Initializable {

    @FXML
    Label ques;
    @FXML
    private Label explain;
    @FXML
    Button A, B, C, D;
    @FXML
    Label score;
    @FXML
    AnchorPane anchorPane;
    private int id = 0;
    private Set<Integer> idSet = new HashSet<>();
    private String ans = "";
    Iterator<Integer> iterator;
    Button clickedButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        A.setStyle("-fx-opacity: 1;");
        B.setStyle("-fx-opacity: 1;");
        C.setStyle("-fx-opacity: 1;");
        D.setStyle("-fx-opacity: 1;");
        start();
    }

    public void getQues() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:fillinblank.sqlite");
        Statement statement = connection.createStatement();

        String query = String.format("SELECT * FROM MultiChoice WHERE ID = %d ", id);
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            ques.setText(resultSet.getString("question"));
            A.setText(resultSet.getString("A"));
            B.setText(resultSet.getString("B"));
            C.setText(resultSet.getString("C"));
            D.setText(resultSet.getString("D"));
            ans = resultSet.getString("answer");
            explain.setText(resultSet.getString("explain"));
        }
    }

    public void start() {
        while (idSet.size() < 10) {
            idSet.add((int) (Math.random() * 80 + 1));
        }
        iterator = idSet.iterator();
        showQues(iterator, null);
    }

    public void showQues(Iterator<Integer> iterator, Button clicked) {
        if (clicked != null) clicked.setStyle("-fx-opacity: 1;");
        A.setDisable(false);
        B.setDisable(false);
        C.setDisable(false);
        D.setDisable(false);

        if (iterator.hasNext()) {
            try {
                id = iterator.next();
                explain.setVisible(false);
                getQues();
            } catch (ClassNotFoundException ignored) {

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            end();
        }
    }

    public void checkAns(ActionEvent e) {
        clickedButton = (Button) e.getSource();
        String userAnswer = clickedButton.getText();
        A.setDisable(true);
        B.setDisable(true);
        C.setDisable(true);
        D.setDisable(true);
        explain.setVisible(true);
        if (ans.equals(userAnswer)) {
            clickedButton.setStyle("-fx-background-color: #77FF77;-fx-opacity: 1;");
        } else {
            clickedButton.setStyle("-fx-background-color: #FF5555;-fx-opacity: 1;");
        }
        anchorPane.requestFocus();
        anchorPane.setOnMouseClicked(event -> {
                showQues(iterator, clickedButton);
                explain.setVisible(false);
                anchorPane.setOnKeyReleased(null);
        });
    }

    private void end() {

    }
}