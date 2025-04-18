package de.dhbw.finanztracker.Domain.account;


import org.apache.commons.lang3.Validate;
import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.Domain.account.transaction.ITransaction;

public class BankAccount implements IAccount {
    private final UUID accountId;
    private final UUID userId;
    private double balance;
    private String accountName;
    private String bankName;
    private List<ITransaction> transactionHistory;

    public BankAccount(UUID userId, double balance, String accountName, String bankName) {
        Validate.notNull(userId, "userId must not be null");
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        Validate.notBlank(bankName, "The name of the Bank must not be blank");

        this.accountId = UUID.randomUUID();
        this.userId = userId;
        this.balance = balance;
        this.accountName = accountName;
        this.bankName = bankName;
    }
    
    public BankAccount(UUID accountId, UUID userId, double balance, String accountName, String bankName) {
        Validate.notNull(accountId, "accountId must not be null");
        Validate.notNull(userId, "userId must not be null");
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        Validate.notBlank(bankName, "The name of the Bank must not be blank");

        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.accountName = accountName;
        this.bankName = bankName;
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
    public Boolean addTransaction(ITransaction transaction) {
        
        balance += transaction.getAmount(); 
        if(balance > 0) {
            transactionHistory.add(transaction); 
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
            return true;
        } else {
            balance -= transaction.getAmount();
            return false;
        }
    }
    
}
