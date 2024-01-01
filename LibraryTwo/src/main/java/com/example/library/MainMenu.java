package com.example.library;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Collections;

public class MainMenu {

    private Dependencies dependecies;

    public MainMenu() {
      this.dependecies = new Dependencies();

    }


    @FXML
    private MenuItem ContactUs;

    @FXML
    private MenuItem deletebook;

    @FXML
    private MenuItem deleteuser;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem registerbook;

    @FXML
    private MenuItem registeruser;

    @FXML
    private MenuItem rentBook;

    @FXML
    private MenuItem sbookrented;

    @FXML
    private MenuItem searchbook;

    @FXML
    private MenuItem searchuser;

    @FXML
    void contactusmenu(ActionEvent event) {
        var messageBox = new Alert(Alert.AlertType.INFORMATION);
        messageBox.setTitle("تماس با ما");
        messageBox.setHeaderText(null);
        messageBox.setContentText("علی خسروی یکتا \n\n شماره تماس : 09384937435");
        messageBox.initStyle(StageStyle.UNDECORATED);
        messageBox.show();


    }

    @FXML
    void deletebook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = dependecies.getDeleteBook();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void deleteuser(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = dependecies.getDeleteUser();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void registerbook(ActionEvent event)throws  IOException {
        FXMLLoader fxmlLoader =dependecies.getRegisterBook();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();

    }
    @FXML
    void registeruser(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = dependecies.getRegisterUser();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void rentBook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = dependecies.getRentBook();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void sbookrented(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = dependecies.getSearchRentBook();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void searchbook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = dependecies.getSearchBook();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void searchuser(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = dependecies.getSearchUser();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();


    }

}
