package de.dhbw.finanztracker.domain.user;

import java.sql.ResultSet;
import java.util.UUID;

public interface IUserRepository {
    ResultSet getAllUsers();
    ResultSet getUserById(UUID accountId);
    void saveUser(String query);
    void deleteUser(UUID accountId);
}
