package de.dhbw.finanztracker.persistance.jdbc.account;

import de.dhbw.finanztracker.domain.account.IAccountRepository;

import java.sql.*;
import java.util.UUID;
import io.github.cdimascio.dotenv.Dotenv;

public class AccountRepository implements IAccountRepository {
    private final String dbUrl ;
    private final String dbUser ;
    private final String dbPassword ;


    AccountRepository() {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("DB_URL");
        this.dbUser = dotenv.get("DB_USER");
        this.dbPassword = dotenv.get("DB_PASSWORD");

    }

    @Override
    public ResultSet getAllAccounts() {
        ResultSet resultSet = null;
        String query = "SELECT * FROM accounts";

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
    public ResultSet getAccountById(UUID accountId) {
        String query = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, accountId.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAccount(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(UUID accountId) {
        String query = "DELETE FROM accounts WHERE account_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, accountId.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}