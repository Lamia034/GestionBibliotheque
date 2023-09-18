package dto;

import java.util.ArrayList;
import java.util.List;

public  class Book {
    private String ISBN;
    private String title;
    private String author;
    private Status status;

    private List<Borrow> borrowing;
    public Book(){}

    public Book(String ISBN, String title, String author, Status status) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.status = status;
        borrowing = new ArrayList<>();
    }



    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    //
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    //
    public enum Status {
        //AVAILABLE, BORROWED, LOST;
        AVAILABLE, BORROWED, LOST ,NOTRETURNED;
    }
}
