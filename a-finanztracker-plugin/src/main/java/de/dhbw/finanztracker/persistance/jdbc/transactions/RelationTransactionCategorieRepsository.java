package de.dhbw.finanztracker.persistance.jdbc.transactions;

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

import de.dhbw.finanztracker.domain.IRepository;
import io.github.cdimascio.dotenv.Dotenv;

public class RelationTransactionCategorieRepsository implements IRepository{
    private final String dbUrl ;
    private final String dbUser ;
    private final String dbPassword ;


    public RelationTransactionCategorieRepsository() {
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("POSTGRES_URL");
        this.dbUser = dotenv.get("POSTGRES_USER");
        this.dbPassword = dotenv.get("POSTGRES_PASSWORD");

    }

    @Override
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM categories";

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
        String query = "SELECT * FROM categories {condition}";
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
            throw new IllegalArgumentException("Expected a Map with keys 'transactionID', 'bankAccountID', and 'categoryName'");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> keyMap = (Map<String, Object>) compositeKey;

        Object transactionID = keyMap.get("transactionID");
        Object bankAccountID = keyMap.get("bankAccountID");
        Object categoryName = keyMap.get("categoryName");

        if (transactionID == null || bankAccountID == null || categoryName == null) {
            throw new IllegalArgumentException("All keys 'transactionID', 'bankAccountID', and 'categoryName' must be provided");
        }

        String query = "DELETE FROM relation_transaction_category WHERE transaction_id = ? AND bank_account_id = ? AND category_name = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, transactionID.toString());
            preparedStatement.setString(2, bankAccountID.toString());
            preparedStatement.setString(3, categoryName.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
