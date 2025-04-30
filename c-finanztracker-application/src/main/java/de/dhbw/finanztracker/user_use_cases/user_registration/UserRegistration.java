package de.dhbw.finanztracker.user_use_cases.user_registration;

import org.apache.commons.lang3.Validate;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;

public class UserRegistration {

    public static User registerUser(String username, IRepository repository) {
        Validate.notEmpty(username,"Username must not be null");

        User user = new User(username);

        String query = "INSERT INTO userTable (user_id, username) VALUES ('" + user.getUserId() + "', '" + user.getUsername() + "')";
        System.err.println(query);
        repository.save(query);

        return user;
    }
    
}
