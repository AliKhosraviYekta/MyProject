package com.example.library;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class RentBook {
    DataBase dataBase = new DataBase();

    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private JFXButton button3;


    @FXML
    private JFXTextField textfield1;

    @FXML
    private JFXTextField textfield2;

    @FXML
    private JFXTextField textfield3;

    @FXML
    private JFXTextField textfield4;

    @FXML
    private JFXTextField textfield5;

    @FXML
    private JFXTextField textfield6;

    @FXML
    void daterent(ActionEvent event) {

    }

    @FXML
    void exitrent(ActionEvent event) {
        Stage stage = (Stage) button2.getScene().getWindow();
        stage.close();

    }

    @FXML
    void famillynamerent(ActionEvent event) {

    }

    @FXML
    void idrent(ActionEvent event) {

    }

    @FXML
    void namebookrent(ActionEvent event) {

    }

    @FXML
    void namerent(ActionEvent event) {

    }

    @FXML
    void nationalidrent(ActionEvent event) {

    }



    @FXML
    void registerrent(ActionEvent event) throws SQLException {
        dataBase.connection.setAutoCommit(false);
        Statement pragmaStatement = dataBase.connection.createStatement();
        pragmaStatement.execute("PRAGMA busy_timeout=5000");
        String First_name = textfield1.getText();
        String last_name = textfield2.getText();
        String name_book = textfield3.getText();
        String id_card = textfield4.getText();
        String date_rent = textfield5.getText();
        String national_id = textfield6.getText();
        if (First_name.isEmpty() || last_name.isEmpty() || name_book.isEmpty() || id_card.isEmpty() || date_rent.isEmpty() || national_id.isEmpty()) {
            Alert messageBox = new Alert(Alert.AlertType.WARNING);
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.setContentText("همه ردیف ها باید تکمیل شود!");
            messageBox.setHeaderText("هشدار!");
            messageBox.show();
            dataBase.connection.commit();
        } else {
            dataBase.connection.setAutoCommit(false);
            Statement pragmaStatement1 = dataBase.connection.createStatement();
            pragmaStatement1.execute("PRAGMA busy_timeout=5000");
            String queryAvBook = "SELECT * FROM Books WHERE name_book = ?";
            PreparedStatement queryOne = dataBase.connection.prepareStatement(queryAvBook);
            queryOne.setString(1, name_book);
            ResultSet resultOne = queryOne.executeQuery();
            if (!resultOne.next()) {
                Alert messageBox = new Alert(Alert.AlertType.WARNING);
                messageBox.initStyle(StageStyle.UNDECORATED);
                messageBox.setContentText("این کتاب موجود نیست!");
                messageBox.setHeaderText("هشدار!");
                messageBox.show();
                dataBase.connection.commit();
            } else {
                dataBase.connection.setAutoCommit(false);
                Statement pragmaStatement3 = dataBase.connection.createStatement();
                pragmaStatement3.execute("PRAGMA busy_timeout=5000");
                String queryAvUsers = "SELECT * FROM users_data WHERE (national_id LIKE ? AND id_card LIKE ?)";
                PreparedStatement queryTwo = dataBase.connection.prepareStatement(queryAvUsers);
                queryTwo.setString(1, national_id);
                queryTwo.setString(2, id_card);
                ResultSet resultTwo = queryTwo.executeQuery();
                if (!resultTwo.next()) {
                    Alert messageBox = new Alert(Alert.AlertType.WARNING);
                    messageBox.initStyle(StageStyle.UNDECORATED);
                    messageBox.setContentText("این کاربر در سیستم ثبت نشده!");
                    messageBox.setHeaderText("هشدار!");
                    messageBox.show();
                    dataBase.connection.commit();
                } else {
                    dataBase.connection.setAutoCommit(false);
                    Statement pragmaStatement4 = dataBase.connection.createStatement();
                    pragmaStatement4.execute("PRAGMA busy_timeout=5000");
                    String queryAvRents = "SELECT * FROM rent_book WHERE name_book = ? AND id_card = ? AND national_id = ?";
                    PreparedStatement query = dataBase.connection.prepareStatement(queryAvRents);
                    query.setString(1, name_book);
                    query.setString(2, id_card);
                    query.setString(3, national_id);
                    ResultSet resultRent = query.executeQuery();

                    if (resultRent.next()) {
                        Alert messageBox = new Alert(Alert.AlertType.WARNING);
                        messageBox.initStyle(StageStyle.UNDECORATED);
                        messageBox.setContentText("این کتاب قبلا به این کاربر اجاره داده شده!");
                        messageBox.setHeaderText("هشدار!");
                        messageBox.show();
                        dataBase.connection.commit();
                    } else {
                        dataBase.connection.setAutoCommit(false);
                        Statement pragmaStatement5 = dataBase.connection.createStatement();
                        pragmaStatement5.execute("PRAGMA busy_timeout=5000");
                        String queryAvRentsBooks = "SELECT * FROM rent_book WHERE name_book LIKE ?";
                        PreparedStatement queryrentbook = dataBase.connection.prepareStatement(queryAvRentsBooks);
                        queryrentbook.setString(1, name_book);
                        ResultSet resultRentbook = queryrentbook.executeQuery();

                        if (resultRentbook.next()) {
                            Alert messageBoxrent = new Alert(Alert.AlertType.WARNING);
                            messageBoxrent.initStyle(StageStyle.UNDECORATED);
                            messageBoxrent.setContentText("این کتاب اجاره داده شده است!");
                            messageBoxrent.setHeaderText("هشدار!");
                            messageBoxrent.show();
                            dataBase.connection.commit();
                        } else {
                            try {
                                dataBase.connection.setAutoCommit(false);
                                Statement pragmaStatement6 = dataBase.connection.createStatement();
                                pragmaStatement6.execute("PRAGMA busy_timeout=5000");
                                String queryRent = "INSERT INTO rent_book (First_name, last_name, name_book, id_card, date_rent, national_id) VALUES (?,?,?,?,?,?)";
                                PreparedStatement preparedStatement = dataBase.connection.prepareStatement(queryRent);
                                preparedStatement.setString(1, First_name);
                                preparedStatement.setString(2, last_name);
                                preparedStatement.setString(3, name_book);
                                preparedStatement.setString(4, id_card);
                                preparedStatement.setString(5, date_rent);
                                preparedStatement.setString(6, national_id);
                                preparedStatement.executeUpdate();
                                Alert messageBox = new Alert(Alert.AlertType.CONFIRMATION);
                                messageBox.initStyle(StageStyle.UNDECORATED);
                                messageBox.setContentText("عملیات ثبت اجاره کتاب با موفقیت انجام شد!");
                                messageBox.setHeaderText("تبریک");
                                messageBox.show();
                                dataBase.connection.commit();
                            } catch (SQLException e) {
                                dataBase.connection.rollback();
                                Alert messageBox = new Alert(Alert.AlertType.ERROR);
                                messageBox.initStyle(StageStyle.UNDECORATED);
                                messageBox.setContentText("عملیات با خطا مواجه شد!");
                                messageBox.setHeaderText("خطا");
                                messageBox.show();
                            } finally {
                                dataBase.connection.setAutoCommit(true);}

                        }
                        }
                    }
                }
            }
        }




    @FXML
    void deleteRentButton(ActionEvent event) throws SQLException {
        Statement pragmaStatement = dataBase.connection.createStatement();
        pragmaStatement.execute("PRAGMA busy_timeout=5000");
        String First_name = textfield1.getText();
        String last_name = textfield2.getText();
        String name_book = textfield3.getText();
        String id_card = textfield4.getText();
        String date_rent = textfield5.getText();
        String national_id = textfield6.getText();
        if (First_name.isEmpty() || last_name.isEmpty() || name_book.isEmpty() || id_card.isEmpty() || date_rent.isEmpty() || national_id.isEmpty()) {
            Alert messageBox = new Alert(Alert.AlertType.WARNING);
            messageBox.initStyle(StageStyle.UNDECORATED);
            messageBox.setContentText("همه ردیف ها باید تکمیل شود!");
            messageBox.setHeaderText("هشدار");
            messageBox.show();

        }
        else{

                String queryCheckuser = "SELECT*FROM users_data WHERE id_card = ? AND national_id = ?";
                PreparedStatement queryConnection = dataBase.connection.prepareStatement(queryCheckuser);
                queryConnection.setString(1, id_card);
                queryConnection.setString(2, national_id);
                ResultSet resultUsers = queryConnection.executeQuery();
                if (!resultUsers.next()) {
                    Alert messageBox = new Alert(Alert.AlertType.WARNING);
                    messageBox.initStyle(StageStyle.UNDECORATED);
                    messageBox.setContentText("این کاربر در سیستم ثبت نشده!");
                    messageBox.setHeaderText("هشدار!");
                    messageBox.show();
                } else {
                    String queryCheckBook = "SELECT*FROM Books WHERE name_book = ? ";
                    PreparedStatement queryConnectionBooks = dataBase.connection.prepareStatement(queryCheckBook);
                    queryConnectionBooks.setString(1, name_book);
                    ResultSet resultSetBooks = queryConnectionBooks.executeQuery();
                    if (!resultSetBooks.next()) {
                        Alert messageBox = new Alert(Alert.AlertType.WARNING);
                        messageBox.initStyle(StageStyle.UNDECORATED);
                        messageBox.setContentText("این کتاب موجود نیست !");
                        messageBox.setHeaderText("هشدار!");
                        messageBox.show();

                    } else {
                        String queryRentBook = "SELECT*FROM rent_book WHERE name_book = ? AND id_card = ? AND national_id = ?";
                        PreparedStatement queryConnectionRentBook = dataBase.connection.prepareStatement(queryRentBook);
                        queryConnectionRentBook.setString(1, name_book);
                        queryConnectionRentBook.setString(2, id_card);
                        queryConnectionRentBook.setString(3, national_id);
                        ResultSet resultRentBook = queryConnectionRentBook.executeQuery();
                        if (!resultRentBook.next()) {
                            Alert messageBox = new Alert(Alert.AlertType.WARNING);
                            messageBox.initStyle(StageStyle.UNDECORATED);
                            messageBox.setContentText("این کتاب به این کاربر اجاره داده نشده !");
                            messageBox.setHeaderText("هشدار !");
                            messageBox.show();
                        } else {
                            try {
                                Statement pragmaStatement1 = dataBase.connection.createStatement();
                                pragmaStatement.execute("PRAGMA busy_timeout=5000");
                                dataBase.connection.setAutoCommit(false);
                                String queryDeleteRent = "DELETE FROM rent_book WHERE name_book = ? AND id_card = ? AND national_id = ? ";
                                PreparedStatement queryConnectionDelete = dataBase.connection.prepareStatement(queryDeleteRent);
                                queryConnectionDelete.setString(1, name_book);
                                queryConnectionDelete.setString(2, id_card);
                                queryConnectionDelete.setString(3, national_id);
                                queryConnectionDelete.executeUpdate();
                                Alert messageBox = new Alert(Alert.AlertType.CONFIRMATION);
                                messageBox.initStyle(StageStyle.UNDECORATED);
                                messageBox.setContentText("عملیات حذف اجاره با موفقیت انجام شد !");
                                messageBox.setHeaderText("تبریک !");
                                messageBox.show();
                                dataBase.connection.commit();
                            } catch (SQLException e) {
                                dataBase.connection.rollback();
                                Alert messageBox = new Alert(Alert.AlertType.ERROR);
                                messageBox.initStyle(StageStyle.UNDECORATED);
                                messageBox.setContentText("عملیات با خطا مواجه شد !");
                                messageBox.setHeaderText("خطا !");
                                messageBox.show();

                            }
                            finally {
                                dataBase.connection.setAutoCommit(true);}
                            }
                            }
                        }
                    }
                }
        }





