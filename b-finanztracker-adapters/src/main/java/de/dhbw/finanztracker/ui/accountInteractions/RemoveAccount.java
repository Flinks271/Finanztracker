package de.dhbw.finanztracker.ui.accountInteractions;

import de.dhbw.finanztracker.account_use_cases.account_manipulation.AccountDeletion;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class RemoveAccount {
    public static void removeAccount(IRepository repository, User user, IAccount account, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();

        System.out.println("Ary you sure you want to delete this account?:");
        System.out.println("y/n:");
        String input = terminalUtility.readLine();
        

        if (input.equals("y")) {
            AccountDeletion.deleteAccount(repository, user, account);
            System.out.println("Account " + account.getAccountName() + " has been deleted successfully.");
        } else if (input.equals("n")) {
            System.out.println("Account deletion cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            removeAccount(repository, user, account, terminalUtility);   
        }
    }

    
}
