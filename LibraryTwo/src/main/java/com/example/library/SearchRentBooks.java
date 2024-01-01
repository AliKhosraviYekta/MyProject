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

public class SearchRentBooks {
         DataBase dataBase = new DataBase();
    @FXML
    private JFXButton butoon2;

    @FXML
    private JFXButton button1;

    @FXML
    private TableView<BooksRent> table1;
    @FXML
    private TableColumn<BooksRent, ?> column1;

    @FXML
    private TableColumn<BooksRent, ?> column2;

    @FXML
    private TableColumn<BooksRent, ?> column3;

    @FXML
    private TableColumn<BooksRent, ?> column4;

    @FXML
    private TableColumn<BooksRent, ?> column5;

    @FXML
    private TableColumn<BooksRent, ?> column6;

    @FXML
    private JFXTextField textFIELD1;


    @FXML
    void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("First_name"));
        column2.setCellValueFactory(new PropertyValueFactory<>("Last_name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("Name_book"));
        column4.setCellValueFactory(new PropertyValueFactory<>("Id_card"));
        column5.setCellValueFactory(new PropertyValueFactory<>("Date_rent"));
        column6.setCellValueFactory(new PropertyValueFactory<>("National_id"));
    }

    @FXML
    void exitBUTTON(ActionEvent event) {
        Stage stage =(Stage) butoon2.getScene().getWindow();
        stage.close();

    }

    @FXML
    void searchBUTTON(ActionEvent event) throws SQLException {
        try {
            dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement1 = dataBase.connection.createStatement();
            pragmaStatement1.execute("PRAGMA busy_timeout=5000");
            String name_book = textFIELD1.getText();
            String query;

            if (name_book.isBlank()) {
                query = "SELECT * FROM rent_book";
            } else {
                query = "SELECT * FROM rent_book WHERE name_book = ?";
            }

            PreparedStatement connectionSearch = dataBase.connection.prepareStatement(query);
            if (!name_book.isBlank()) {
                connectionSearch.setString(1, name_book);

            }
            ResultSet resultSet = connectionSearch.executeQuery();
            table1.getItems().clear();
            if (!resultSet.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("هیچ کتابی با این مشخصات اجاره داده نشده!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                dataBase.connection.commit();
            } else {
                do {
                    String First_name = resultSet.getString("First_name");
                    String Last_name = resultSet.getString("last_name");
                    String Name_book = resultSet.getString("name_book");
                    String Id_card = resultSet.getString("id_card");
                    String Date_rent = resultSet.getString("date_rent");
                    String National_id = resultSet.getString("national_id");
                    BooksRent rentbooks = new BooksRent(First_name,Last_name,Name_book,Id_card,Date_rent,National_id);
                    table1.getItems().add(rentbooks);
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

    }




