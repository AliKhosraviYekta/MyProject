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

public class RegisterBook {
    DataBase dataBase = new DataBase();

    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private JFXTextField textFIELD3;

    @FXML
    private JFXTextField textfield1;

    @FXML
    private JFXTextField textfield2;

    @FXML
    private JFXTextField textfield4;

    @FXML
    private JFXTextField textfield5;

    @FXML
    void dateRELEASEFIELD(ActionEvent event) {

    }

    @FXML
    void exitBUTTON(ActionEvent event) {
        Stage stage =(Stage) button2.getScene().getWindow();
        stage.close();

    }

    @FXML
    void genreFIELD(ActionEvent event) {

    }

    @FXML
    void nameBOOKFIELD(ActionEvent event) {

    }

    @FXML
    void publisherFIELD(ActionEvent event) {

    }

    @FXML
    void registerBUTTON(ActionEvent event) throws SQLException {
        dataBase.connection.setAutoCommit(false);
        Statement pragmaStatement1 = dataBase.connection.createStatement();
        pragmaStatement1.execute("PRAGMA busy_timeout=5000");
        String name_book = textfield1.getText();
        String writer = textfield2.getText();
        String publisher_book = textFIELD3.getText();
        String date_release =textfield4.getText();
        String genre = textfield5.getText();
        if (name_book.isEmpty()){
            Alert messageBox = new Alert(Alert.AlertType.WARNING);
            messageBox.setContentText("ردیف کتاب نمیتواند خالی باشد!");
            messageBox.setHeaderText("هشدار!");
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.show();
            dataBase.connection.commit();
        }
         else {
             dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement = dataBase.connection.createStatement();
            pragmaStatement.execute("PRAGMA busy_timeout=5000");
             String selectQuery ="SELECT*FROM Books WHERE name_book = ?";
            PreparedStatement query = dataBase.connection.prepareStatement(selectQuery);
            query.setString(1,name_book);
            ResultSet resultSet = query.executeQuery();
             if (resultSet.next()){
                 Alert messageBox = new Alert(Alert.AlertType.WARNING);
                 messageBox.setContentText("این کتاب در سیستم وجود دارد!");
                 messageBox.setHeaderText("هشدار!");
                 messageBox.initStyle(StageStyle.UNDECORATED);
                 messageBox.show();
                 dataBase.connection.commit();
             }
             else {
                 try {
                     dataBase.connection.setAutoCommit(false);
                     Statement pragmaStatement2 = dataBase.connection.createStatement();
                     pragmaStatement2.execute("PRAGMA busy_timeout=5000");
                     String queryBook ="INSERT INTO Books (name_book,writer,publisher_book,date_release,genre) VALUES(?,?,?,?,?)";
                     PreparedStatement queryReader = dataBase.connection.prepareStatement(queryBook);
                     queryReader.setString(1,name_book);
                     queryReader.setString(2,writer);
                     queryReader.setString(3,publisher_book);
                     queryReader.setString(4,date_release);
                     queryReader.setString(5,genre);
                     queryReader.executeUpdate();
                     Alert messageBox = new Alert(Alert.AlertType.CONFIRMATION);
                     messageBox.setContentText("عملیات ثبت کتاب با موفقیت انجام شد!" );
                     messageBox.setHeaderText("تبریک!");
                     messageBox.initStyle(StageStyle.UNDECORATED);
                     messageBox.show();
                     dataBase.connection.commit();

                 } catch (SQLException e) {
                     dataBase.connection.rollback();
                     Alert messageBox = new Alert(Alert.AlertType.ERROR);
                     messageBox.initStyle(StageStyle.UNDECORATED);
                     messageBox.setHeaderText("خظا!");
                     messageBox.setContentText("عملیات با خطا مواجه شد!");
                     messageBox.show();

                 }
                  finally {
                     dataBase.connection.setAutoCommit(true);;
                 }
             }
        }
    }

    @FXML
    void writerFIELD(ActionEvent event) {

    }

}
