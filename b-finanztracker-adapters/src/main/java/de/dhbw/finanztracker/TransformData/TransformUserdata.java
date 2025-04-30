package de.dhbw.finanztracker.TransformData;

import de.dhbw.finanztracker.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransformUserdata {


    public List<User> TransformUsers(List<Map<String, Object>> rows) {
        List<User> users = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            users.add(TransformUser(map));
        }   
        return users;
    }
    
    public User TransformUser(Map<String, Object> rows) {
        User user = null;
        
        UUID userId = UUID.fromString((String) rows.get("user_id"));
        String username = (String) rows.get("username");
        user = new User(userId, username);

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
