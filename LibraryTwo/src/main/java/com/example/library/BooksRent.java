package com.example.library;

public class BooksRent {
    String First_name;
    String Last_name;
    String Name_book;
    String Id_card;
    String Date_rent;
    String National_id;



    public BooksRent(String First_name,String Last_name , String Name_book , String Id_card, String Date_rent,String National_id){
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.Name_book = Name_book;
        this.Id_card = Id_card;
        this.Date_rent = Date_rent;
        this.National_id = National_id;

           }
    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getName_book() {
        return Name_book;
    }

    public void setName_book(String name_book) {
        Name_book = name_book;
    }

    public String getId_card() {
        return Id_card;
    }

    public void setId_card(String id_card) {
        Id_card = id_card;
    }

    public String getDate_rent() {
        return Date_rent;
    }

    public void setDate_rent(String date_rent) {
        Date_rent = date_rent;
    }

    public String getNational_id() {
        return National_id;
    }

    public void setNational_id(String national_id) {
        National_id = national_id;
    }



}
