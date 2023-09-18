package dto;

import java.util.Date;

public class Borrow {

    private Client client;
   // private Integer NumMember;
    private Book book;
  //  private String ISBN;
    private Date dateb;
    private Date dater;

    public Borrow() {}

    public Borrow(Client client, Book book, Date dateb, Date dater) {
        this.client = client;
        this.book = book;
        this.dateb = dateb;
        this.dater = dater;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateb() {
        return dateb;
    }

    public void setDateb(Date dateb) {
        this.dateb = dateb;
    }

    public Date getDater() {
        return dater;
    }

    public void setDater(Date dater) {
        this.dater = dater;
    }
}
