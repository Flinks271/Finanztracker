package de.dhbw.finanztracker.controller.TransformData;

import de.dhbw.finanztracker.domain.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransformUserdata {


    public List<User> TransformUsers(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        User user = null;
    
        while ((user = TransformUser(resultSet)) != null) {
            users.add(user);
        }
        return users;
    }
    
    public User TransformUser(ResultSet resultSet) {
        User user = null;
    
        try {
            if (resultSet.next()) { // Prüfen, ob es eine nächste Zeile gibt
                UUID userId = UUID.fromString(resultSet.getString("user_id"));
                String username = resultSet.getString("username");
    
                // Erstelle ein neues User-Objekt
                user = new User(userId, username, new ArrayList<>());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return user;
    }
    
    public String generateInsertQuery(User user) {
        String query = "INSERT INTO user (user_id, username) " +
                       "VALUES ({user_id}, {username})";
    
        query = query.replace("{user_id}", "'" + user.getUserId().toString() + "'");
        query = query.replace("{username}", "'" + user.getUsername() + "'");
        return query;
    }
    
}
