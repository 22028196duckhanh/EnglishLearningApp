module Server {
    requires javafx.controls;
    requires javafx.fxml;


    opens Server to javafx.fxml;
    exports Server;
}