package de.dhbw.finanztracker.Domain.user;


import org.apache.commons.lang3.Validate;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final String username;

    public User(String username) {
        Validate.notNull(username, "Username must not be null");

        this.userId = UUID.randomUUID();
        this.username = username;
    }

    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
}
