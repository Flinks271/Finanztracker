package de.dhbw.finanztracker.account_use_cases.account_manipulation;

import java.util.Map;
import java.util.UUID;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.BankAccount;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;

public class AccountManagement {
    public static void deleteAccount(IRepository repository, User user, IAccount account) {
        repository.deleteById(account.getAccountId());
        user.removeAccount(account);        
    } 

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

    public static void updateAccount(IRepository repository, IAccount account, Map<String, String> accountData) {
        account.setAccountname(accountData.get("account_name"));
        account.setBankName(accountData.get("bank_name"));
        account.setBalance(Double.parseDouble(accountData.get("balance")));
        String query = "UPDATE accounts SET balance = '" + account.getBalance() + "', account_name = '" + account.getAccountName() + "', bank_name = '" + account.getBankName() + "' WHERE account_id = '" + account.getAccountId() + "'";
        repository.save(query);        
    }
}
