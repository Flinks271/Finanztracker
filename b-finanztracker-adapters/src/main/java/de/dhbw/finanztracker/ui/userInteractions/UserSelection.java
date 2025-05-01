package de.dhbw.finanztracker.ui.userInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.List;
import java.util.Scanner;

public class UserSelection {

    public static User selectUser(List<User> users, IRepository repository) {
        TerminalUtility.clearScreen();
        User user = null;

        System.out.println("Please select a user: ");
        System.out.println("0. Register new user");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getUsername());
        }

        int selectedIndex = TerminalUtility.readNextInt() - 1;

        if (selectedIndex >= 0 && selectedIndex < users.size()) {
            user = users.get(selectedIndex);
            String selectedUsername = (String) user.getUsername();
            System.out.println("User " + selectedUsername + " has been selected successfully.");

            TerminalUtility.pauseForOneSecond(); 
            
        } else if(selectedIndex == -1){
            user = NewUserRegistration.registerNewUser(repository);
        }else {
            System.out.println("Invalid selection. Please try again.");
            TerminalUtility.pauseForOneSecond();
            selectUser(users, repository);
        }

        

        return user;
    }
    
}
