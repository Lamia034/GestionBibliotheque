package implementations;
import java.util.List;
import java.util.ArrayList;

import dto.Book;
import interfaces.BookInterface;
import dto.Borrow;
import interfaces.BorrowInterface;
import dto.Client;
import interfaces.ClientInterface;
import java.util.Date;
import helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class BookImplementation implements BookInterface {
    private DatabaseConnection db;

    public BookImplementation() {
        db = DatabaseConnection.getInstance();
    }

    @Override

    public Book add(Book book) {
                  Connection conn = db.getConnection();
            String insertQuery = "INSERT INTO Books (title, author, isbn, status) VALUES (?, ?, ?, ?)";

            try{

                PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setString(3, book.getISBN());
                preparedStatement.setString(4,book.getStatus().name());

                int rowsInserted = preparedStatement.executeUpdate();


                if (rowsInserted > 0) {
                    return book;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book searchByISBN(String isbn) {
        try {
            Connection conn = db.getConnection();
            String searchQuery = "SELECT * FROM Books WHERE ISBN = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {
                preparedStatement.setString(1, isbn);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    //create object
                    String foundISBN = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String statusStr = resultSet.getString("status");
                    Book.Status status = Book.Status.valueOf(statusStr);

                    return new Book(foundISBN, title, author, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Book update(Book book) {
        Connection conn = db.getConnection();
        String updateQuery = "UPDATE Books SET title = ?, author = ?, status = ? WHERE isbn = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getStatus().name());
            preparedStatement.setString(4, book.getISBN());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteByISBN(String isbn) {
        Connection conn = db.getConnection();
        String deleteQuery = "DELETE FROM Books WHERE isbn = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

            preparedStatement.setString(1, isbn);

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Book> getAllBooks() {
        Connection conn = db.getConnection();
        String selectQuery = "SELECT * FROM Books";
        List<Book> books = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();



            while (resultSet.next()) {

                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String status = resultSet.getString("status");

                Book book = new Book();
                book.setISBN(isbn);
                book.setTitle(title);
                book.setAuthor(author);

                if (status != null) {
                    book.setStatus(Book.Status.valueOf(status));
                }
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getAvailableBooks() {
        Connection conn = db.getConnection();
        String selectQuery = "SELECT * FROM Books WHERE status = 'AVAILABLE'";
        List<Book> books = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String status = resultSet.getString("status");

                Book book = new Book();
                book.setISBN(isbn);
                book.setTitle(title);
                book.setAuthor(author);

                if (status != null) {
                    book.setStatus(Book.Status.valueOf(status));
                }

                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Borrow> getBorrowedBooks() {
        List<Borrow> borrowedBooks = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT B.title ,B.author  ,B.isbn  , BR.dateb , BR.dater ,BO.name , BO.phone  FROM Borrowing BR JOIN Books B ON BR.isbn = B.isbn JOIN    Borrowers BO ON BR.nummember = BO.nummember WHERE  B.status = 'BORROWED';")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                Date dateb = resultSet.getDate("dateb");
                Date dater = resultSet.getDate("dater");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");

              Book book = new Book();
                book.setISBN(isbn);
                book.setTitle(title);
                book.setAuthor(author);

                Client client = new Client();
                client.setName(name);
                client.setPhone(phone);

                Borrow borrow = new Borrow();
                borrow.setClient(client);
                borrow.setBook(book);
                borrow.setDateb(dateb);
                borrow.setDater(dater);

                borrowedBooks.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowedBooks;
    }


    @Override
    public int getAvailableBookCount() {
        int count = 0;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) FROM Books WHERE status = 'AVAILABLE'";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int getBorrowedBookCount() {
        int count = 0;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) FROM Books WHERE status = 'BORROWED'";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int getLostBookCount() {
        int count = 0;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) FROM Books WHERE status = 'LOST'";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    @Override
    public int getAllBooksCount() {
        int count = 0;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) AS total FROM Books";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }




    public void updateNotReturnedBooks() {
        try (Connection conn = db.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE Books SET status = 'NOTRETURNED' " +
                             "WHERE status = 'BORROWED' AND isbn IN (SELECT isbn FROM Borrowing WHERE dater < NOW())"
             )) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getNRBookCount() {
        int count = 0;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) FROM Books WHERE status = 'NOTRETURNED'";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}





