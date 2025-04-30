package de.dhbw.finanztracker.account_use_cases.account_manipulation;

import java.util.Map;
import java.util.UUID;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.BankAccount;

public class AccountCreation {

    public static BankAccount createAccount(Map<String, String> accountData, IRepository repository, UUID userId) {
        BankAccount account = new BankAccount(  Double.parseDouble(accountData.get("balance")), 
                                                accountData.get("account_name"), 
                                                accountData.get("bank_name"));
        
        String query = "INSERT INTO accounts (account_id, user_id, balance, account_name, bank_name, counter) VALUES " +
                       "('" + account.getAccountId() + "', '" + userId + "', '" + account.getBalance() + "', '" + account.getAccountName() + "', '" 
                       + account.getBankName() + "', '" + account.getCounter() + "')";
        System.err.println(query);
        repository.save(query);           

        return account;
    }  
}
