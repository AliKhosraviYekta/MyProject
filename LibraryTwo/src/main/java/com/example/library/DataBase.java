package com.example.library;
 import javafx.scene.control.Alert;
 import javafx.stage.StageStyle;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;


public class DataBase {
    private final String DB_URL = "jdbc:sqlite:library.db";

 public  Connection connection;

    public DataBase() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);



        } catch (SQLException e) {
            Alert messageBox = new Alert(Alert.AlertType.ERROR);
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.setHeaderText("خطای سیستم");
            messageBox.setContentText("خطا در هنگام اتصال به پایگاه داده");
            messageBox.show();


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
