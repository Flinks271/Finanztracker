package de.dhbw.finanztracker.domain.account;

import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;

public interface IAccount {
    UUID getAccountId();
    double getBalance();
    String getAccountNumber();
    Integer getCounter();
    String getAccountName();
    String getBankName();
    List<ITransaction> getTransactionHistory();
    Boolean addTransaction(ITransaction transaction);
    Boolean removeTransaction(ITransaction transaction);
    String toTerminalString();
}
