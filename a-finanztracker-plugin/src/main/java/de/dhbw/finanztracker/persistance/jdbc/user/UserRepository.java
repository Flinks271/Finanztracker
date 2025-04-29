package de.dhbw.finanztracker.persistance.jdbc.user;

import de.dhbw.finanztracker.domain.user.IUserRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.UUID;

public class UserRepository implements IUserRepository {
    private final String dbUrl ;
    private final String dbUser ;
    private final String dbPassword ;

    UserRepository() {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("DB_URL");
        this.dbUser = dotenv.get("DB_USER");
        this.dbPassword = dotenv.get("DB_PASSWORD");
    }

    @Override
    public ResultSet getAllUsers() {
        ResultSet resultSet = null;
        String query = "SELECT * FROM user";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet getUserById(UUID userID) {
        String query = "SELECT * FROM user WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userID.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveUser(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(UUID accountId) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, accountId.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
