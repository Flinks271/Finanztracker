package de.dhbw.finanztracker.persistance.jdbc.user;

import de.dhbw.finanztracker.domain.user.IUserRepository;

import java.sql.ResultSet;
import java.util.UUID;

public class UserRepository implements IUserRepository {

    @Override
    public ResultSet getAllUsers() {
        // Implement the logic to retrieve all users from the database
        return null; // Placeholder return statement
    }

    @Override
    public ResultSet getUserById(UUID accountId) {
        // Implement the logic to retrieve a user by ID from the database
        return null; // Placeholder return statement
    }

    @Override
    public void saveUser(String query) {
        // Implement the logic to save a user to the database
    }

    @Override
    public void deleteUser(UUID accountId) {
        // Implement the logic to delete a user from the database
    }
    
}
