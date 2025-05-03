package de.dhbw.finanztracker.user_use_cases.user_deletion;

import java.util.List;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;

public class UserDeletion {
    public static void deleteUser(IRepository repository, List<User> users, User user) {
        repository.deleteById(user.getUserId());
        users.remove(user);       
    }
}
