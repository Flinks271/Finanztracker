package de.dhbw.finanztracker.domain.account;

import java.util.List;

public interface IAccountRepository {
    List<IAccount> getAllAccounts();
    IAccount getAccountById(String accountId);
    void saveAccount(IAccount account);
    void deleteAccount(String accountId);
}
