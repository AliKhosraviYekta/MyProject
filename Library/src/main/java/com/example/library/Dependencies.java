package com.example.library;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
public class Dependencies {

    public Dependencies(){

    }

    public FXMLLoader getRegisterUser() throws IOException {
        return new FXMLLoader(RegisterUser.class.getResource("Register.fxml"));
    }

    public FXMLLoader getRegisterBook()throws IOException{
       return   new FXMLLoader(RegisterBook.class.getResource("Registerbook.fxml"));

    }

    public FXMLLoader getRentBook()throws IOException {
        return  new FXMLLoader(RentBook.class.getResource("Rentbook.fxml"));

    }

    public FXMLLoader getDeleteBook() throws IOException {
        return new FXMLLoader(DeleteBook.class.getResource("deletebook.fxml"));

    }
      public FXMLLoader getDeleteUser()throws IOException{
        return new FXMLLoader(DeleteUser.class.getResource("deleteuser.fxml"));
      }

      public FXMLLoader getSearchUser() throws IOException{
        return new FXMLLoader(SearchUser.class.getResource("Searchuser.fxml"));
      }

      public FXMLLoader getSearchBook()throws IOException{
        return  new FXMLLoader(SearchBook.class.getResource("Searchbook.fxml"));
      }

      public FXMLLoader getSearchRentBook()throws IOException{
        return new FXMLLoader(SearchRentBooks.class.getResource("Rentbooksview.fxml"));

      }
}
