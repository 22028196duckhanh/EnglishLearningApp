module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jlayer;


    opens Server to javafx.fxml;
    exports Server;
    exports Controller;
    opens Controller to javafx.fxml;
}