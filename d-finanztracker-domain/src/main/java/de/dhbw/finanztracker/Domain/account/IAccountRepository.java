package de.dhbw.finanztracker.domain.account;

import java.sql.ResultSet;
import java.util.UUID;

public interface IAccountRepository {
    ResultSet getAllAccounts();
    ResultSet getAccountById(UUID accountId);
    void saveAccount(String query);
    void deleteAccount(UUID accountId);
}
