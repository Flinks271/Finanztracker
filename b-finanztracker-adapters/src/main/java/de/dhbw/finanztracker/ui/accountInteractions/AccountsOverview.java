package de.dhbw.finanztracker.ui.accountInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;

public class AccountsOverview {

    public static void showAccounts(User user, List<IRepository> repositories, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();

        List<IAccount> accounts = user.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found for user: " + user.getUsername());
            System.out.println("Please create a new account.");
            CreateNewAccount.createAccount(repositories.get(0), user,terminalUtility);
            return;
        }
        
        showAccountsTable(accounts);
    }



    public static void showAccountsTable(List<IAccount> accounts) {
  
        int maxIndexWidth = String.valueOf(accounts.size()).length();
        maxIndexWidth = Math.max(maxIndexWidth, 2); 
        int maxNameWidth = accounts.stream()
                .map(a -> a.getAccountName().length())
                .max(Integer::compare)
                .orElse(0);
        maxNameWidth = Math.max(maxNameWidth, 12);
        int maxBankNameWidth = accounts.stream()
                .map(a -> a.getBankName().length())
                .max(Integer::compare)
                .orElse(0);
        maxBankNameWidth = Math.max(maxBankNameWidth, 9);
        DoubleAdder totalBalance = new DoubleAdder();
        int maxBalanceWidth = accounts.stream()
                .peek(a -> totalBalance.add(a.getBalance()))
                .map(a -> String.valueOf(a.getBalance()).length())
                .max(Integer::compare)
                .orElse(0);
        maxBalanceWidth = Math.max(maxBalanceWidth, 7);
    
        System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + "s %n",
                "Nr", "Account Name","Bank Name", "Balance");
        System.out.println("-".repeat(maxIndexWidth) + "-+-" + "-".repeat(maxNameWidth) + "-+-" + "-".repeat(maxBankNameWidth) + "-+---" + "-".repeat(maxBalanceWidth));
    
        for (int i = 0; i < accounts.size(); i++) {
            IAccount acc = accounts.get(i);
            System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + ".2f € %n",
                    i + 1, acc.getAccountName(), acc.getBankName(), acc.getBalance());
        }
        System.out.println("-".repeat(maxIndexWidth) + "-┴-" + "-".repeat(maxNameWidth) + "-┴-" + "-".repeat(maxBankNameWidth) + "-+---" + "-".repeat(maxBalanceWidth));
    
        System.out.printf("%-" + (maxIndexWidth + maxNameWidth + maxBankNameWidth + 6) + "s | %-" + maxBalanceWidth + ".2f € %n",
            "Total Balance",  totalBalance.doubleValue());
    }
    
}
