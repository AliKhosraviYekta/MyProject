package com.example.library;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchBook{
        DataBase dataBase = new DataBase();
    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private TableView<Books> table1;


    @FXML
    private TableColumn<Books, ?> column1;

    @FXML
    private TableColumn<Books, ?> column2;

    @FXML
    private TableColumn<Books, ?> column3;

    @FXML
    private TableColumn<Books, ?> column4;

    @FXML
    private TableColumn<Books, ?> column5;

    @FXML
    private JFXTextField textFIELD1;

    @FXML
    private JFXTextField textFIELD2;


    @FXML
    void exitBUTTON(ActionEvent event) {
        Stage stage =(Stage) button2.getScene().getWindow();
        stage.close();

    }

    @FXML
    void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("Name_book"));
        column2.setCellValueFactory(new PropertyValueFactory<>("Writer"));
        column3.setCellValueFactory(new PropertyValueFactory<>("Publisher_book"));
        column4.setCellValueFactory(new PropertyValueFactory<>("Date_release"));
        column5.setCellValueFactory(new PropertyValueFactory<>("Genre"));

    }

    @FXML
    void nameBOOK(ActionEvent event) {

    }

    @FXML
    void searchBUTTON(ActionEvent event) throws SQLException {
        try {
            dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement1 = dataBase.connection.createStatement();
            pragmaStatement1.execute("PRAGMA busy_timeout=5000");
            String name_book = textFIELD1.getText();
            String writer = textFIELD2.getText();
            String query;

            if (name_book.isBlank() && writer.isBlank()) {
                query = "SELECT * FROM Books";
            } else {
                query = "SELECT * FROM Books WHERE name_book = ? OR writer = ?";
            }

            PreparedStatement connectionSearch = dataBase.connection.prepareStatement(query);
            if (!name_book.isBlank() || !writer.isBlank()) {
                connectionSearch.setString(1, name_book);
                connectionSearch.setString(2, writer);
            }
            ResultSet resultSet = connectionSearch.executeQuery();
            table1.getItems().clear();
            if (!resultSet.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("هیچ کتابی با این مشخصات یافت نشد!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                dataBase.connection.commit();
            } else {
                do {
                    String Name_book = resultSet.getString("name_book");
                    String Writer = resultSet.getString("writer");
                    String Publisher_book = resultSet.getString("publisher_book");
                    String Date_release = resultSet.getString("date_release");
                    String Genre = resultSet.getString("genre");

                     Books books = new Books(Name_book,Writer,Publisher_book,Date_release,Genre);
                    table1.getItems().add(books);
                } while (resultSet.next());
            }
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

    @FXML
    void tableVIEWBOOK(ActionEvent event) {

    }

}
