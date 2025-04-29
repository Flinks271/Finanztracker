package de.dhbw.finanztracker.domain.account;

import java.util.List;
import java.util.UUID;

public interface IAccountRepository {
    List<IAccount> getAllAccounts();
    IAccount getAccountById(UUID accountId);
    void saveAccount(IAccount account);
    void deleteAccount(UUID accountId);
}
