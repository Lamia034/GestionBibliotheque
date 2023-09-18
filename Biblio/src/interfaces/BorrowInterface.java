package interfaces;

import dto.Book;
import dto.Borrow;

import java.util.List;

public interface BorrowInterface {
     boolean borrowBook(Integer clientMemberNumber, String bookISBN);
     boolean returnBook(String bookISBN);



}
