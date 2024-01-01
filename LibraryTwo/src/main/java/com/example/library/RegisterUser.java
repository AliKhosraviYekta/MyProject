package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterUser {
    DataBase dataBase = new DataBase();

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private TextField textfield1;

    @FXML
    private TextField textfield2;

    @FXML
    private TextField textfield3;

    @FXML
    private TextField textfield4;

    @FXML
    private TextField textfield5;

    @FXML
    private TextField textfield6;

    @FXML
    private TextField textfield7;

    @FXML
    void addressfield(ActionEvent event) {

    }

    @FXML
    void datefield(ActionEvent event) {

    }

    @FXML
    void exitButton(ActionEvent event) {
        Stage stage = (Stage) button2.getScene().getWindow();
        stage.close();
    }

    @FXML
    void familyfield(ActionEvent event) {

    }

    @FXML
    void idcardfield(ActionEvent event) {

    }

    @FXML
    void namefield(ActionEvent event) {

    }

    @FXML
    void nationalidfield(ActionEvent event) {

    }

    @FXML
    void phonenumberfield(ActionEvent event) {

    }

    @FXML
    void registerBUTTON(ActionEvent event) throws SQLException {
        dataBase.connection.setAutoCommit(false);
        Statement pragmaStatement0 = dataBase.connection.createStatement();
        pragmaStatement0.execute("PRAGMA busy_timeout=5000");
        String First_name = textfield1.getText();
        String last_name = textfield2.getText();
        String national_id = textfield3.getText();
        String id_card = textfield4.getText();
        String phone_number = textfield5.getText();
        String date_birth = textfield6.getText();
        String address = textfield7.getText();
        if (id_card.isEmpty() || national_id.isEmpty()) {
            Alert messageBox = new Alert(Alert.AlertType.WARNING);
            messageBox.setContentText("ردیف های کد ملی و کد عضویت نمیتواند خالی باشد!");
            messageBox.setHeaderText("هشدار!");
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.show();
            dataBase.connection.commit();
        } else {
            dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement7 = dataBase.connection.createStatement();
            pragmaStatement7.execute("PRAGMA busy_timeout=5000");
            String selectQuery = "SELECT*FROM users_data WHERE id_card =? OR national_id=?";
            PreparedStatement selectStatement = dataBase.connection.prepareStatement(selectQuery);
            selectStatement.setString(1, id_card);
            selectStatement.setString(2, national_id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("این کاربر در سیستم وجود دارد!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                dataBase.connection.commit();
            } else {
                try {
                    dataBase.connection.setAutoCommit(false);
                    Statement pragmaStatement6 = dataBase.connection.createStatement();
                    pragmaStatement6.execute("PRAGMA busy_timeout=5000");
                    String query = "INSERT INTO users_data (First_name,last_name,national_id,id_card,phone_number,date_birth,address) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = dataBase.connection.prepareStatement(query);
                    preparedStatement.setString(1, First_name);
                    preparedStatement.setString(2, last_name);
                    preparedStatement.setString(3, national_id);
                    preparedStatement.setString(4, id_card);
                    preparedStatement.setString(5, phone_number);
                    preparedStatement.setString(6, date_birth);
                    preparedStatement.setString(7, address);
                    preparedStatement.executeUpdate();
                    Alert messageBox = new Alert(Alert.AlertType.CONFIRMATION);
                    messageBox.initStyle(StageStyle.UNDECORATED);
                    messageBox.setContentText("عملیات ثبت با موفقیت انجام شد!");
                    messageBox.show();
                    dataBase.connection.commit();

                } catch (SQLException e) {
                    dataBase.connection.rollback();
                    Alert messageBox = new Alert(Alert.AlertType.ERROR);
                    messageBox.initStyle(StageStyle.UNDECORATED);
                    messageBox.setContentText("عملیات با خطا مواجه شد!");
                    messageBox.setHeaderText("خطا!");
                    messageBox.show();
                }
                   finally {
                    dataBase.connection.setAutoCommit(true);
                }
            }

        }
    }
}