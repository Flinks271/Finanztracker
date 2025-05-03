package de.dhbw.finanztracker.account_use_cases.account_manipulation;


import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;

public class AccountDeletion {
    public static void deleteAccount(IRepository repository, User user, IAccount account) {
        repository.deleteById(account.getAccountId());
        user.removeAccount(account);        
    } 
}
