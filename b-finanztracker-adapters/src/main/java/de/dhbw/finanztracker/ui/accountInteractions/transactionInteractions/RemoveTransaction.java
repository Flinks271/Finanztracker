package de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.transaction_use_cases.TransactionManagement;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class RemoveTransaction {
    public static void removeTransaction(IRepository repository, IAccount account, ITransaction transaction, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();

        System.out.println("Ary you sure you want to delete this transaction?:");
        System.out.println("y/n:");
        String input = terminalUtility.readLine();
        

        if (input.equals("y")) {
            TransactionManagement.deleteTransaction(repository, account, transaction);
            System.out.println("Transaction: " + transaction.getDescription() + " has been deleted successfully.");
        } else if (input.equals("n")) {
            System.out.println("Transaction deletion cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            removeTransaction(repository, account, transaction, terminalUtility);
        }
    }   

}
