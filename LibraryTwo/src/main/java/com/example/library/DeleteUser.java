package com.example.library;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUser {
    DataBase dataBase = new DataBase();
    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private JFXTextField textFIELD1;

    @FXML
    void deleteuser(ActionEvent event) throws SQLException {
        dataBase.connection.setAutoCommit(false);
        Statement pragmaStatement0 = dataBase.connection.createStatement();
        pragmaStatement0.execute("PRAGMA busy_timeout=5000");
        String national_id = textFIELD1.getText();
        if (national_id.isEmpty()) {
            Alert messageBox = new Alert(Alert.AlertType.WARNING);
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.setContentText("ردیف کد ملی باید تکمیل شود!");
            messageBox.setHeaderText("هشدار!");
            messageBox.show();
            dataBase.connection.commit();
        } else {
            dataBase.connection.setAutoCommit(false);
            String queryCheckUser = "SELECT* FROM users_data WHERE national_id = ?";
            PreparedStatement connectionCheckUser = dataBase.connection.prepareStatement(queryCheckUser);
            connectionCheckUser.setString(1, national_id);
            ResultSet resultSet = connectionCheckUser.executeQuery();
            if (!resultSet.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("این کاربر در سیستم موجود نمی باشد!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                 dataBase.connection.commit();
            } else {
                dataBase.connection.setAutoCommit(false);
                String queryCheck = "SELECT * FROM rent_book WHERE national_id = ?";
                PreparedStatement connectionCheck = dataBase.connection.prepareStatement(queryCheck);
                connectionCheck.setString(1, national_id);
                ResultSet resultSet1 = connectionCheck.executeQuery();
                if (resultSet1.next()) {
                    Alert messageBox = new Alert(Alert.AlertType.WARNING);
                    messageBox.initStyle(StageStyle.UNDECORATED);
                    messageBox.setContentText("این کاربر یک یا چند کتاب اجاره کرده نمیتوانید حذف کنید!");
                    messageBox.setHeaderText("هشدار!");
                    messageBox.show();
                    dataBase.connection.commit();
                } else {

                    try {
                        Statement pragmaStatement = dataBase.connection.createStatement();
                        pragmaStatement.execute("PRAGMA busy_timeout=5000");
                        String queryDelete = "DELETE  FROM users_data WHERE national_id = ?";
                        PreparedStatement conncetionDelete = dataBase.connection.prepareStatement(queryDelete);
                        conncetionDelete.setString(1, national_id);
                        conncetionDelete.executeUpdate();
                        Alert messageBox = new Alert(Alert.AlertType.CONFIRMATION);
                        messageBox.initStyle(StageStyle.UNDECORATED);
                        messageBox.setContentText("عملیات حذف کاربر با موفقیت انجام شد!");
                        messageBox.setHeaderText("تبریک!");
                        messageBox.show();


                    } catch (SQLException e) {
                        dataBase.connection.rollback();
                        Alert messageBox = new Alert(Alert.AlertType.ERROR);
                        messageBox.initStyle(StageStyle.UNDECORATED);
                        messageBox.setContentText("عملیات با خطا مواجه شد!");
                        messageBox.setHeaderText("خطا!");
                        messageBox.show();

                    }
                     finally {
                        dataBase.connection.setAutoCommit(true);;
                    }

                }


            }


        }
         }



    @FXML
    void exituser(ActionEvent event) {
        Stage stage = (Stage) button2.getScene().getWindow();
        stage.close();

    }


}
