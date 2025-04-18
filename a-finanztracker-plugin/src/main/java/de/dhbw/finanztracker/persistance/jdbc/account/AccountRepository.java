package de.dhbw.finanztracker.persistance.jdbc.account;


import de.dhbw.finanztracker.domain.account.BankAccount;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.IAccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    public List<IAccount> getAllAccounts() {
        List<IAccount> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                UUID accountId = UUID.fromString(resultSet.getString("account_id"));
                UUID userId = UUID.fromString(resultSet.getString("user_id"));
                double balance = resultSet.getDouble("balance");
                String accountName = resultSet.getString("account_name");
                String bankName = resultSet.getString("bank_name");

                
                IAccount account = new BankAccount(accountId, userId, balance, accountName, bankName, 0, new ArrayList<>());
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public IAccount getAccountById(String accountId) {
        String query = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UUID userId = UUID.fromString(resultSet.getString("user_id"));
                    double balance = resultSet.getDouble("balance");
                    String accountName = resultSet.getString("account_name");
                    String bankName = resultSet.getString("bank_name");

                    
                    return new BankAccount(UUID.fromString(accountId), userId, balance, accountName, bankName, 0, new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAccount(IAccount account) {
        String query = "INSERT INTO accounts (account_id, user_id, balance, account_name, bank_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, account.getAccountNumber().toString());
            preparedStatement.setString(2, account.getUserId().toString());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.setString(4, account.getAccountName());
            preparedStatement.setString(5, account.getBankName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(String accountId) {
        String query = "DELETE FROM accounts WHERE account_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}