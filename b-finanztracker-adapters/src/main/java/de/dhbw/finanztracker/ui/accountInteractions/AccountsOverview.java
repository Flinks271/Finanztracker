package de.dhbw.finanztracker.ui.accountInteractions;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

public class AccountsOverview {

    public static void showAccounts(User user, Map<String, IRepository> repositories, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();

        List<IAccount> accounts = user.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found for user: " + user.getUsername());
            System.out.println("Please create a new account.");
            CreateNewAccount.createAccount(repositories.get("accountRepository"), user,terminalUtility);
            return;
        }
        
        showAccountsTable(accounts, terminalUtility);

    }



    public static void showAccountsTable(List<IAccount> accounts, TerminalUtility terminalUtility) {
        
        System.out.println("Accounts Overview.");
  
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
        List<Integer> distance = List.of(maxIndexWidth + 1, maxNameWidth + 2, maxBankNameWidth + 2, maxBalanceWidth + 3);
        System.out.println(terminalUtility.generateHyphenLineOneSide(distance));
    
        for (int i = 0; i < accounts.size(); i++) {
            IAccount acc = accounts.get(i);
            System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + ".2f € %n",
                    i + 1, acc.getAccountName(), acc.getBankName(), acc.getBalance());
        }
        System.out.println("-".repeat(maxIndexWidth) + "-┴-" + "-".repeat(maxNameWidth) + "-┴-" + "-".repeat(maxBankNameWidth) + "-+---" + "-".repeat(maxBalanceWidth));
    
        System.out.printf("%-" + (maxIndexWidth + maxNameWidth + maxBankNameWidth + 6) + "s | %-" + maxBalanceWidth + ".2f € %n",
            "Total Balance",  totalBalance.doubleValue());
    }

    public static String inputCommands(TerminalUtility terminalUtility, List<IAccount> accounts) {
        System.out.println("Please select the Account you want to edit or enter other commands:");
        String command = null;

        String input = terminalUtility.readLine();

        if (input.matches("\\d+")) { 
            command = input;
            int InputInt = Integer.parseInt(command) - 1;
            if (InputInt >=0 && InputInt < accounts.size()) {
                System.out.println("You have selected account: " + accounts.get(InputInt).getAccountName());
            } else {
                System.out.println("Invalid account number. Please try again.");
                System.out.println("For help with the commands you may use 'h' or 'help'.");
                return inputCommands(terminalUtility, accounts);
            }
        } else {
            switch (input) {
                case "create":
                case "c":
                    command = "c";
                    break;
        
                case "sign out":
                case "s":
                    command = "s";
                    break;
        
                case "h":
                case "help":
                    System.out.println("Available commands:");
                    System.out.println("'" + accounts.size() + "': Select an account by number.");
                    System.out.println("'c' or 'create': Create a new Account.");
                    System.out.println("'s' or 'sign out': Sign your current user out and return to user selection.");
                    return inputCommands(terminalUtility, accounts);
        
                default:
                    System.out.println("Invalid input. Please try again.");
                    System.out.println("For help with the commands you may use 'h' or 'help'.");
                    return inputCommands(terminalUtility, accounts);
            }
        }

        return command;
    }
    
}
