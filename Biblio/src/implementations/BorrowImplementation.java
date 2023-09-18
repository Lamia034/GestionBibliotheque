package implementations;
import java.util.List;
import java.util.ArrayList;
import dto.Client;
import dto.Book;

import dto.Borrow;
import interfaces.BorrowInterface;
import helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class BorrowImplementation implements BorrowInterface {
    private DatabaseConnection db;

    public BorrowImplementation() {
        db = DatabaseConnection.getInstance();
    }

   /* @Override
    public boolean borrowBook(Integer clientMemberNumber, String bookISBN) {
        Connection conn = db.getConnection();
        String insertQuery = "INSERT INTO Borrowing (nummember, isbn, dateb, dater) VALUES (?, ?, NOW(), NOW() + INTERVAL '5 days')";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, clientMemberNumber);
            preparedStatement.setString(2, bookISBN);

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0; // Return true if the insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception appropriately
        }
    }*/
    @Override
    public boolean returnBook(String bookISBN) {
        Connection conn = db.getConnection();

        String updateQuery = "UPDATE Books SET status = 'AVAILABLE' WHERE isbn = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, bookISBN);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean borrowBook(Integer clientMemberNumber, String bookISBN) {
        Connection conn = db.getConnection();

        if (isBookBorrowed(bookISBN)) {

            return false;
        }

        String insertQuery = "INSERT INTO Borrowing (nummember, isbn, dateb, dater) VALUES (?, ?, NOW(), NOW() + INTERVAL '1 day')";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, clientMemberNumber);
            preparedStatement.setString(2, bookISBN);

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isBookBorrowed(String bookISBN) {
        Connection conn = db.getConnection();
        String query = "SELECT status FROM Books WHERE ISBN = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, bookISBN);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String status = resultSet.getString("status");
                return "BORROWED".equals(status) || "NOTRETURNED".equals(status) || "LOST".equals(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
