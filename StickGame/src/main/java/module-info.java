module com.example.stickgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.stickgame to javafx.fxml;
    exports com.example.stickgame;
}