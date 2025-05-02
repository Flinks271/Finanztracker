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

public class ReaccuringRepository implements IRepository {
    private final String dbUrl ;
    private final String dbUser ;
    private final String dbPassword ;


    public ReaccuringRepository() {
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("POSTGRES_URL");
        this.dbUser = dotenv.get("POSTGRES_USER");
        this.dbPassword = dotenv.get("POSTGRES_PASSWORD");

    }

    @Override
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM reaccuring";

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
        String query = "SELECT * FROM reaccuring {condition}";
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
    public void deleteById(Object compositeKey) {
        if (!(compositeKey instanceof Map<?, ?>)) {
            throw new IllegalArgumentException("Expected a Map with keys 'reaccuringID' and 'bankAccountID'");
        }
    
        @SuppressWarnings("unchecked")
        Map<String, Object> keyMap = (Map<String, Object>) compositeKey;
        Object reaccuringID = keyMap.get("reaccuringID");
        Object bankAccountID = keyMap.get("bankAccountID");
    
        if (reaccuringID == null || bankAccountID == null) {
            throw new IllegalArgumentException("Both 'reaccuringID' and 'bankAccountID' must be provided");
        }
    
        String query = "DELETE FROM reaccuring WHERE reaccuring_id = ? AND bank_account_id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setString(1, reaccuringID.toString());
            preparedStatement.setString(2, bankAccountID.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}