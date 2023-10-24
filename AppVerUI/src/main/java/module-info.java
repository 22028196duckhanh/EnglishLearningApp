module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jlayer;
    requires org.jsoup;
    requires javafx.web;
    requires javafx.media;

    opens Server to javafx.fxml;
    exports Server;
    exports Controller;
    opens Controller to javafx.fxml;
}