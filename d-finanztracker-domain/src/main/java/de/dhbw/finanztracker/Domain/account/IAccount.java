package de.dhbw.finanztracker.domain.account;

import java.util.List;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;

public interface IAccount {
    double getBalance();
    String getAccountNumber();
    Integer getCounter();
    List<ITransaction> getTransactionHistory();
    Boolean addTransaction(ITransaction transaction);
    Boolean removeTransaction(ITransaction transaction);
}
