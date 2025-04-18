package de.dhbw.finanztracker.domain.account;


import org.apache.commons.lang3.Validate;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class BankAccount implements IAccount {
    private final UUID accountId;
    private final UUID userId;
    private double balance;
    private String accountName;
    private String bankName;
    private int counter;
    private List<ITransaction> transactionHistory;

    public BankAccount(UUID userId, double balance, String accountName, String bankName) {
        Validate.notNull(userId, "userId must not be null");
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        Validate.notBlank(bankName, "The name of the Bank must not be blank");
        Validate.isTrue(balance > 0, "Balance must be positive, but was: %s", balance);

        this.accountId = UUID.randomUUID();
        this.userId = userId;
        this.balance = balance;
        this.accountName = accountName;
        this.bankName = bankName;
        this.counter = 0;
        this.transactionHistory = new ArrayList<ITransaction>();
    }
    
    public BankAccount(UUID accountId, UUID userId, double balance, String accountName, String bankName, int counter, ArrayList<ITransaction> transactionHistory) {
        Validate.notNull(accountId, "accountId must not be null");
        Validate.notNull(userId, "userId must not be null");
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        Validate.notBlank(bankName, "The name of the Bank must not be blank");
        Validate.isTrue(balance > 0, "Balance must be positive, but was: %s", balance);

        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.accountName = accountName;
        this.bankName = bankName;
        this.counter = counter;
        this.transactionHistory = transactionHistory;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountNumber() {
        return accountId.toString();
    }

    @Override
    public List<ITransaction> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public Integer getCounter() {
        return counter;
    }

    @Override
    public Boolean addTransaction(ITransaction transaction) {
        
        balance += transaction.getAmount(); 
        if(balance > 0) {
            transactionHistory.add(transaction);
            counter++;
            return true;
        } else {
            balance -= transaction.getAmount();
            return false;
        }
    }

    @Override
    public Boolean removeTransaction(ITransaction transaction) {
        
        balance += transaction.getAmount(); 
        if(balance > 0) {
            transactionHistory.remove(transaction); 
            counter--;
            return true;
        } else {
            balance -= transaction.getAmount();
            return false;
        }
    }
    
}
