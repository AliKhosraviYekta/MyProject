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

public class SearchUser {
    DataBase dataBase = new DataBase();
    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private TableView<User> table1;

    @FXML
    private TableColumn<User, ?> column1;

    @FXML
    private TableColumn<User, ?> column2;

    @FXML
    private TableColumn<User, ?> column3;

    @FXML
    private TableColumn<User, ?> column4;

    @FXML
    private TableColumn<User, ?> column5;

    @FXML
    private TableColumn<User, ?> column6;

    @FXML
    private TableColumn<User, ?> column7;


    @FXML
    private JFXTextField textFIELD1;

    @FXML
    private JFXTextField textFIELD2;

    @FXML
    void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("First_name"));
        column2.setCellValueFactory(new PropertyValueFactory<>("Last_name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("National_id"));
        column4.setCellValueFactory(new PropertyValueFactory<>("Id_card"));
        column5.setCellValueFactory(new PropertyValueFactory<>("Phone_number"));
        column6.setCellValueFactory(new PropertyValueFactory<>("Address"));
        column7.setCellValueFactory(new PropertyValueFactory<>("Date_birth"));
    }


    @FXML
    void exitBUTTON(ActionEvent event) {
        Stage stage = (Stage) button2.getScene().getWindow();
        stage.close();

    }

    @FXML
    void idCARDFIELD(ActionEvent event) {

    }

    @FXML
    void nationalID(ActionEvent event) {

    }
    @FXML
    void searchBUTTON(ActionEvent event) throws SQLException {
        try {
            dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement1 = dataBase.connection.createStatement();
            pragmaStatement1.execute("PRAGMA busy_timeout=5000");
            String national_id = textFIELD1.getText();
            String id_card = textFIELD2.getText();
            String query;

            if (national_id.isBlank() && id_card.isBlank()) {
                query = "SELECT * FROM users_data";
            } else {
                query = "SELECT * FROM users_data WHERE national_id = ? OR id_card = ?";
            }

            PreparedStatement connectionSearch = dataBase.connection.prepareStatement(query);
            if (!national_id.isBlank() || !id_card.isBlank()) {
                connectionSearch.setString(1, national_id);
                connectionSearch.setString(2, id_card);
            }
            ResultSet resultSet = connectionSearch.executeQuery();
            table1.getItems().clear();
            if (!resultSet.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("هیچ کاربری با این مشخصات یافت نشد!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                dataBase.connection.commit();
            } else {
                do {
                    String First_name = resultSet.getString("First_name");
                    String last_name = resultSet.getString("last_name");
                    String nationalID = resultSet.getString("national_id");
                    String idCard = resultSet.getString("id_card");
                    String phone_number = resultSet.getString("phone_number");
                    String address = resultSet.getString("address");
                    String dateBirth = resultSet.getString("date_birth");

                    User users = new User(First_name, last_name, nationalID, idCard, phone_number, address, dateBirth);
                    table1.getItems().add(users);
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
        void tableVIEWSEAERCH (ActionEvent event){

        }
    }





