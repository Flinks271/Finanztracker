package de.dhbw.finanztracker.domain.user;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {
    List<User> getAllUsers();
    User getUserById(UUID accountId);
    void saveUser(User account);
    void deleteUser(UUID accountId);
}
