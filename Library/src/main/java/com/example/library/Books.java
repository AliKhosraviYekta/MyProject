package com.example.library;

public class Books {
     private String Name_book;
     private String Writer;
     private String Publisher_book;
     private String Date_release;
     private String Genre ;

       public Books(String name_book,String writer,String publisher_book,String date_release,String genre){
           this.Name_book = name_book;
           this.Writer = writer;
           this.Publisher_book = publisher_book;
           this.Date_release = date_release;
           this.Genre = genre;


       }

    public String getName_book() {
        return Name_book;
    }

    public void setName_book(String name_book) {
        this.Name_book = name_book;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        this.Writer = writer;
    }

    public String getPublisher_book() {
        return Publisher_book;
    }

    public void setPublisher_book(String publisher_book) {
        this.Publisher_book = publisher_book;
    }

    public String getDate_release() {
        return Date_release;
    }

    public void setDate_release(String date_release) {
        this.Date_release = date_release;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre = genre;
    }
}
