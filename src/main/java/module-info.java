module com.example.fishmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;


    opens com.example.fishmanagement.controller to javafx.fxml;
    opens com.example.fishmanagement.model to javafx.base, javafx.fxml;
    exports com.example.fishmanagement;
}