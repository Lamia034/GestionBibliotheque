
package implementations;

import dto.Book;
import dto.Client;
import interfaces.ClientInterface;
import helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class ClientImplementation implements ClientInterface {
    private DatabaseConnection db;

    public ClientImplementation() {
        db = DatabaseConnection.getInstance();
    }

    @Override
    public Integer add(Client client) {
        Connection conn = db.getConnection();
        String insertQuery = "INSERT INTO Borrowers (name, phone) VALUES (?, ?) RETURNING nummember";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhone());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("nummember");
            } else {

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public Client searchByNum(Integer searchNum) {
        try {
            Connection conn = db.getConnection();
            String searchQuery = "SELECT * FROM borrowers WHERE nummember = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {
                preparedStatement.setInt(1, searchNum);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Integer foundNum = resultSet.getInt("nummember");
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");

                    Client foundClient = new Client(name, phone);
                    foundClient.setNumMember(foundNum);

                    return foundClient;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Client update(Client client) {
        Connection conn = db.getConnection();
        String updateQuery = "UPDATE borrowers SET name = ?, phone = ? WHERE nummember = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhone());
            preparedStatement.setInt(3, client.getNumMember());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteByN(Integer deleteN) {
        Connection conn = db.getConnection(); // Get the database connection from DatabaseConnection
        String deleteQuery = "DELETE FROM Borrowers WHERE nummember = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, deleteN);

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception appropriately
        }
    }





}