package com.example.library;

public class User {
    private String First_name;
    private String last_name;
    private String national_id;
    private String id_card;
    private String phone_number;
    private String address;
    private String date_birth;

    public User(String First_name,String last_name,String national_id,String id_card,String phone_number, String address,String date_birth){
        this.First_name = First_name;
        this.last_name =last_name;
        this.national_id = national_id;
        this.id_card = id_card;
        this.phone_number = phone_number;
        this.address = address;
        this.date_birth = date_birth;

                     }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String user_name) {
        this.First_name = First_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }


}
