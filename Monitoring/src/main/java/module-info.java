module com.example.monitoring {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.oshi;
    exports com.example.monitoring;
    opens com.example.monitoring to javafx.fxml;

}

