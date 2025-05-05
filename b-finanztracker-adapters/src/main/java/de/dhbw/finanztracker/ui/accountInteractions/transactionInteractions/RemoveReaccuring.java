package de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.transaction_use_cases.ReaccuringManagement;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class RemoveReaccuring {
    public static void removeReaccuring(IRepository repository, IAccount account, IReaccuring reaccuring, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();

        System.out.println("Ary you sure you want to delete this transaction?:");
        System.out.println("y/n:");
        String input = terminalUtility.readLine();
        

        if (input.equals("y")) {
            ReaccuringManagement.deleteReaccuring(repository, account, reaccuring);
            System.out.println("Reaccuring: " + reaccuring.getName() + " has been deleted successfully.");
        } else if (input.equals("n")) {
            System.out.println("Reaccuring deletion cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            removeReaccuring(repository, account, reaccuring, terminalUtility);
        }
    }   

}
