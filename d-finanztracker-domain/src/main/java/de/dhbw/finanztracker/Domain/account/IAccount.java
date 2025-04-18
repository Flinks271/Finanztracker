package de.dhbw.finanztracker.Domain.account;

import java.util.List;
import de.dhbw.finanztracker.Domain.account.transaction.ITransaction;

interface IAccount {
    double getBalance();
    String getAccountNumber();
    Integer getCounter();
    List<ITransaction> getTransactionHistory();
    Boolean addTransaction(ITransaction transaction);
    Boolean removeTransaction(ITransaction transaction);
}
