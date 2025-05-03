package de.dhbw.finanztracker.ui.userInteractions;

import java.util.List;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.user_use_cases.user_deletion.UserDeletion;

public class RemoveUser {
    public static void removeAccount(IRepository repository, List<User> users, User user, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();

        System.out.println("Ary you sure you want to delete this user?:");
        System.out.println("y/n:");
        String input = terminalUtility.readLine();
        

        if (input.equals("y")) {
            UserDeletion.deleteUser(repository, users, user);
            System.out.println("User " + user.getUsername() + " has been deleted successfully.");
        } else if (input.equals("n")) {
            System.out.println("User deletion cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            removeAccount(repository, users, user, terminalUtility);   
        }
    }
}
