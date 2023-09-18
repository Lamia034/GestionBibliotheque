package interfaces;
import java.util.List;

import dto.Book;
import dto.Borrow;

public interface BookInterface {
    Book add(Book book);
    Book searchByISBN(String isbn);
    Book update(Book book);
    boolean deleteByISBN(String isbn);

    List<Book> getAllBooks();

    List<Book> getAvailableBooks();

    List<Borrow> getBorrowedBooks();

    int  getAvailableBookCount();
    int  getBorrowedBookCount();
    int  getLostBookCount();

    int getAllBooksCount();

    void updateNotReturnedBooks();

    int getNRBookCount();

}
