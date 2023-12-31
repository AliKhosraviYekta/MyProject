module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;


    opens com.example.library to javafx.fxml;
    exports com.example.library;
}