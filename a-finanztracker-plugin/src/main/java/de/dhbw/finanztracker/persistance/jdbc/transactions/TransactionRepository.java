package de.dhbw.finanztracker.persistance.jdbc.transactions;

import de.dhbw.finanztracker.domain.IRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionRepository implements IRepository {
    private final String dbUrl ;
    private final String dbUser ;
    private final String dbPassword ;


    public TransactionRepository() {
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("POSTGRES_URL");
        this.dbUser = dotenv.get("POSTGRES_USER");
        this.dbPassword = dotenv.get("POSTGRES_PASSWORD");

    }

    @Override
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM transactions";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public List<Map<String, Object>> getCond(String condition) {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM transactions {condition}";
        query = query.replace("{condition}", condition);

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public void save(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void deleteById(Object transactionId) {
        if (!(transactionId instanceof UUID)) {
            throw new IllegalArgumentException("Transaction ID must be an UUID");
        }
        String query = "DELETE FROM transactions WHERE transaction_id = ?";
        query = query.replace("?", "'" + transactionId.toString() + "'");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, transactionId.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
