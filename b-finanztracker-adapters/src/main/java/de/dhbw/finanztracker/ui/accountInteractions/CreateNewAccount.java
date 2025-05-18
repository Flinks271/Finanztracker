package de.dhbw.finanztracker.ui.accountInteractions;

import de.dhbw.finanztracker.account_use_cases.account_manipulation.AccountManagement;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.HashMap;
import java.util.Map;

public class CreateNewAccount {

    public static void createAccount(IRepository repository, User user, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();
        IAccount account = null;

        System.out.println("Please select the Accounttype from the following Types:");
        System.out.println("1. Bankaccount:");
        int input = terminalUtility.readNextInt();
        
        

        if (input == 1) {
            Map<String, String> inputs = getBasicinputs(terminalUtility);
            account = AccountManagement.createAccount(inputs, repository, user.getUserId());
            System.out.println("Account " + account.getAccountName() + " has been created successfully.");
        } else {
            createAccount(repository, user, terminalUtility);
        }   

        terminalUtility.pauseForOneSecond();

        if (account != null) {
            user.addAccount(account);
        } else {
            System.out.println("No account was added.");
        }
    }

    public static Map<String, String> getBasicinputs(TerminalUtility terminalUtility) {

        Map<String, String> inputs = new HashMap<>();

        System.out.println("Please enter the account name: ");
        String inputName = "";
        do {
            inputName = terminalUtility.readLine();
            if (inputName == null || inputName.isEmpty()) {
                System.out.println("Account name cannot be empty. Please try again.");
                terminalUtility.pauseForOneSecond();
            }
        } while (inputName == null || inputName.isEmpty());
        inputs.put("account_name", inputName);

        System.out.println("Please enter the bank name: ");
        String inputBankName = "";
        do {
            inputBankName = terminalUtility.readLine();
            if (inputBankName == null || inputBankName.isEmpty()) {
                System.out.println("Bank name cannot be empty. Please try again.");
                terminalUtility.pauseForOneSecond();
            }
        } while (inputBankName == null || inputBankName.isEmpty());
        inputs.put("bank_name", inputBankName);

        String inputBalance = null;
        do {
            System.out.println("Please enter the balance: ");
            System.out.println("The allowed form for the Balance is Double with 2 digits following the point: ");
            inputBalance = terminalUtility.readLine();

        } while (!inputBalance.matches("^-?\\d+(\\.|,)?(\\d{1,2})?$"));
        inputs.put("balance", inputBalance.replace(",", "."));

        
        return inputs;
    }
}