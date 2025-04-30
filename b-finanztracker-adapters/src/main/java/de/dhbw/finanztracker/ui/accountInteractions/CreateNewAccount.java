package de.dhbw.finanztracker.ui.accountInteractions;

import de.dhbw.finanztracker.account_use_cases.account_manipulation.AccountCreation;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateNewAccount {

    public static void createAccount(IRepository repository, User user,Scanner scanner) {
        TerminalUtility.clearScreen();
        IAccount account = null;


        
        try {
            System.out.println("Please select the Accounttype from the following Types:");
            System.out.println("1. Bankaccount:");
            int input = scanner.nextInt();
            scanner.nextLine(); 
            
            
            Map<String, String> inputs = getBasicinputs(scanner);

            if (input == 1) {
                account = AccountCreation.createAccount(inputs, repository, user.getUserId());
                System.out.println("Account " + account.getAccountName() + " has been created successfully.");
            }            

            Thread.sleep(1000);

        } catch (Exception e) {
            System.err.println("An error occurred during account creation: " + e.getMessage());
        }

        if (account != null) {
            user.addAccount(account);
        } else {
            System.out.println("No account was added.");
        }
    }

    public static Map<String, String> getBasicinputs(Scanner scanner) {

        Map<String, String> inputs = new HashMap<>();

        try {
            System.out.println("Please enter the account name: ");
            String inputName = scanner.nextLine();
            inputs.put("account_name", inputName);

            System.out.println("Please enter the bank name: ");
            String inputBankName = scanner.nextLine();
            inputs.put("bank_name", inputBankName);

            String inputBalance = null;
            do {
                System.out.println("Please enter the balance: ");
                System.out.println("The allowed form for the Balance is Double with 2 digits following the point: ");
                inputBalance = scanner.nextLine();

            } while (!inputBalance.matches("^\\d+(\\.|,)?(\\d{1,2})?$"));
            inputs.put("balance", inputBalance.replace(",", "."));

        } catch (Exception e) {
            System.err.println("An error occurred during account creation: " + e.getMessage());
        }
        return inputs;
    }
}