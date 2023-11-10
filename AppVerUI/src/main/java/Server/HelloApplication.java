package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class HelloApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/menu-view.fxml")));
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });


        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);


        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Dictionary Application");

        //Image icon = new Image("icon.png");
        //primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, IOException {
        //DictionaryManagement.dictionary = new DatabaseDictionary();
        //DictionaryManagement.dictionary.init();
        //DictionaryManagement.display();
        //GameManagement.launch();
        launch();
    }
}