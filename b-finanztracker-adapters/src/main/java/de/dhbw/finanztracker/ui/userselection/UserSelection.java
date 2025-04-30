package de.dhbw.finanztracker.ui.userselection;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.user_use_cases.user_registration.UserRegistration;
import de.dhbw.finanztracker.domain.user.User;

public class UserSelection {
    
    public static void newUserRegistration(IRepository repository) {
        System.out.println("Please enter a username: ");
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            String inputUsername = scanner.nextLine();
            User user = UserRegistration.registerUser(inputUsername, repository);
            System.out.println("User " + inputUsername + " has been registered successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred during user registration: " + e.getMessage());
        }
    }
}
