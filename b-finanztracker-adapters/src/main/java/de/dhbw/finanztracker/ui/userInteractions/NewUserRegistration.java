package de.dhbw.finanztracker.ui.userInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.user_use_cases.user_registration.UserRegistration;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class NewUserRegistration {
    
    public static User registerNewUser(IRepository repository) {
        User user = null;
        TerminalUtility.clearScreen();
        System.out.println("Please enter a username: ");
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {

            String inputUsername = scanner.nextLine();
            user = UserRegistration.registerUser(inputUsername, repository);
            System.out.println("User " + inputUsername + " has been registered successfully.");
            
            Thread.sleep(1000); 
            
        } catch (Exception e) {
            System.err.println("An error occurred during user registration: " + e.getMessage());
        }
        return user;
    }
}
