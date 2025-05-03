package de.dhbw.finanztracker.ui.accountInteractions;

import java.util.HashMap;
import java.util.Map;

import de.dhbw.finanztracker.account_use_cases.account_manipulation.AccountManagement;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class UpdateAccount {
    
    public static void updateAccount(IRepository repository, IAccount account, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();


        System.out.println("Ary you sure you want to update The Data in this account?:");
        System.out.println("y/n:");
        String input = terminalUtility.readLine();
        
        

        if (input.equals("y")) {
            Map<String, String> inputs = getBasicinputs(terminalUtility);
            AccountManagement.updateAccount(repository, account, inputs);
            System.out.println("Account " + account.getAccountName() + " has been updated successfully.");
        } else if (input.equals("n")) {
            System.out.println("Account update cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            updateAccount(repository, account, terminalUtility);   
        }
    }

    public static Map<String, String> getBasicinputs(TerminalUtility terminalUtility) {

        Map<String, String> inputs = new HashMap<>();

        System.out.println("Please enter the account name: ");
        String inputName = terminalUtility.readLine();
        inputs.put("account_name", inputName);

        System.out.println("Please enter the bank name: ");
        String inputBankName = terminalUtility.readLine();
        inputs.put("bank_name", inputBankName);

        String inputBalance = null;
        do {
            System.out.println("Please enter the balance: ");
            System.out.println("The allowed form for the Balance is Double with 2 digits following the point: ");
            inputBalance = terminalUtility.readLine();

        } while (!inputBalance.matches("^\\d+(\\.|,)?(\\d{1,2})?$"));
        inputs.put("balance", inputBalance.replace(",", "."));

        
        return inputs;
    }
}
