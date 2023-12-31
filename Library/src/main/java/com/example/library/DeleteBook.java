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

public class DeleteBook  {
      DataBase dataBase = new DataBase();
    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private JFXTextField textFIELD1;


    @FXML
    void deleteBUTTONBOOK(ActionEvent event) throws SQLException {
        dataBase.connection.setAutoCommit(false);
        Statement pragmaStatement0 = dataBase.connection.createStatement();
        pragmaStatement0.execute("PRAGMA busy_timeout=5000");
        String name_book = textFIELD1.getText();
        if (name_book.isEmpty()) {
            Alert messageBox = new Alert(Alert.AlertType.WARNING);
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.setContentText("ردیف کتاب باید تکمیل شود!");
            messageBox.setHeaderText("هشدار!");
            messageBox.show();
            dataBase.connection.commit();
        } else {
            dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement3 = dataBase.connection.createStatement();
            pragmaStatement3.execute("PRAGMA busy_timeout=5000");
            String queryCheckBook = "SELECT* FROM Books WHERE name_book = ?";
            PreparedStatement connectionCheckBook = dataBase.connection.prepareStatement(queryCheckBook);
            connectionCheckBook.setString(1, name_book);
            ResultSet resultSet = connectionCheckBook.executeQuery();
            if (!resultSet.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("این کتاب موجود نمی باشد!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                dataBase.connection.commit();
            } else {
                Statement pragmaStatement2 = dataBase.connection.createStatement();
                pragmaStatement2.execute("PRAGMA busy_timeout=5000");
                String queryCheck = "SELECT * FROM rent_book WHERE name_book = ?";
                PreparedStatement connectionCheck = dataBase.connection.prepareStatement(queryCheck);
                connectionCheck.setString(1, name_book);
                ResultSet resultSet1 = connectionCheck.executeQuery();
                if (resultSet1.next()) {
                    Alert messageBox = new Alert(Alert.AlertType.WARNING);
                    messageBox.initStyle(StageStyle.UNDECORATED);
                    messageBox.setContentText("این کتاب تحت اجاره میباشد نمیتوانید حذف کنید!");
                    messageBox.setHeaderText("هشدار!");
                    messageBox.show();
                } else {

                    try {
                        dataBase.connection.setAutoCommit(false);
                        Statement pragmaStatement1 = dataBase.connection.createStatement();
                        pragmaStatement1.execute("PRAGMA busy_timeout=5000");
                        String queryDelete = "DELETE  FROM Books WHERE name_book = ?";
                        PreparedStatement conncetionDelete = dataBase.connection.prepareStatement(queryDelete);
                        conncetionDelete.setString(1, name_book);
                        conncetionDelete.executeUpdate();
                        Alert messageBox = new Alert(Alert.AlertType.CONFIRMATION);
                        messageBox.initStyle(StageStyle.UNDECORATED);
                        messageBox.setContentText("عملیات حذف کتاب با موفقیت انجام شد!");
                        messageBox.setHeaderText("تبریک!");
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

        @FXML
        void exitBUTTON (ActionEvent event){
            Stage stage = (Stage) button2.getScene().getWindow();
            stage.close();

        }
    }
