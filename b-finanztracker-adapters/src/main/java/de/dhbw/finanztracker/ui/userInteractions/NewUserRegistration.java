package de.dhbw.finanztracker.ui.userInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.user_use_cases.user_registration.UserRegistration;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class NewUserRegistration {
    
    public static User registerNewUser(IRepository repository, TerminalUtility terminalUtility) {
        User user = null;
        terminalUtility.clearScreen();
        System.out.println("Please enter a username: ");

        String inputUsername = TerminalUtility.readLine();
        user = UserRegistration.registerUser(inputUsername, repository);
        System.out.println("User " + inputUsername + " has been registered successfully.");
        
        TerminalUtility.pauseForOneSecond();   
        
        return user;
    }
}
