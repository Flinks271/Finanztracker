package de.dhbw.finanztracker.domain.account;

import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;

public interface IAccount {
    UUID getAccountId();
    double getBalance();
    String getAccountNumber();
    Integer getCounter();
    String getAccountName();
    String getBankName();
    List<IReaccuring> getReaccuringTransactions();
    List<ITransaction> getTransactionHistory();
    Boolean addReaccuringTransaction(IReaccuring reaccuringTransaction);
    Boolean removeReaccuringTransaction(IReaccuring reaccuringTransaction);
    Boolean addTransaction(ITransaction transaction);
    Boolean removeTransaction(ITransaction transaction);
    String toTerminalString();
    void setTransactions(List<ITransaction> transactions);
    void setReaccuring(List<IReaccuring> reaccurings);
}
