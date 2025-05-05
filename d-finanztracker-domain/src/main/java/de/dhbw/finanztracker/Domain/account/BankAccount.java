package de.dhbw.finanztracker.domain.account;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;

import org.apache.commons.lang3.Validate;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class BankAccount implements IAccount {
    private final UUID accountId;
    private double balance;
    private String accountName;
    private String bankName;
    private int counter;
    private List <IReaccuring> reaccuringTransactions;
    private List<ITransaction> transactionHistory;

    
    public BankAccount( double balance, String accountName, String bankName) {
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        Validate.notBlank(bankName, "The name of the Bank must not be blank");
        Validate.isTrue(balance > 0, "Balance must be positive, but was: %s", balance);

        this.accountId = UUID.randomUUID();
        this.balance = balance;
        this.accountName = accountName;
        this.bankName = bankName;
        this.counter = 0;
        this.transactionHistory = new ArrayList<ITransaction>();
        this.reaccuringTransactions = new ArrayList<IReaccuring>();
    }
    
    public BankAccount(UUID accountId, double balance, String accountName, String bankName, int counter, ArrayList<ITransaction> transactionHistory, ArrayList<IReaccuring> reaccuringTransactions) {
        Validate.notNull(accountId, "accountId must not be null");
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        Validate.notBlank(bankName, "The name of the Bank must not be blank");
        Validate.isTrue(balance > 0, "Balance must be positive, but was: %s", balance);

        this.accountId = accountId;
        this.balance = balance;
        this.accountName = accountName;
        this.bankName = bankName;
        this.counter = counter;
        this.transactionHistory = transactionHistory;
        this.reaccuringTransactions = reaccuringTransactions;
    }

    @Override
    public UUID getAccountId() {
        return accountId;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public String getBankName() {
        return bankName;
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
    public void setTransactions(List<ITransaction> transactions) {
        this.transactionHistory = transactions;
    }

    @Override
    public void setReaccuring(List<IReaccuring> reaccurings) {
        this.reaccuringTransactions = reaccurings;
    }

    @Override
    public void setAccountName(String accountName) {
        Validate.notBlank(accountName, "The name of the Account must not be blank");
        this.accountName = accountName;
    }

    @Override
    public void setBankName(String bankName) {
        Validate.notBlank(bankName, "The name of the Bank must not be blank");
        this.bankName = bankName;
    }

    @Override
    public void setBalance(double balance) {
        Validate.isTrue(balance >= 0, "Balance must not be negative, but was: %s", balance);
        this.balance = balance;
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
        
        balance -= transaction.getAmount(); 
        if(balance > 0) {
            transactionHistory.remove(transaction); 
            counter--;
            return true;
        } else {
            balance -= transaction.getAmount();
            return false;
        }
    }

    @Override
    public List<IReaccuring> getReaccuringTransactions() {
        return reaccuringTransactions;
    }

    @Override
    public Boolean addReaccuringTransaction(IReaccuring reaccuringTransaction) {
        return reaccuringTransactions.add(reaccuringTransaction);
    }

    @Override
    public Boolean removeReaccuringTransaction(IReaccuring reaccuringTransaction) {
        return reaccuringTransactions.remove(reaccuringTransaction);
    }

    @Override
    public String toTerminalString() {
        return  accountName + "|" +
               bankName + "|" +
               balance + "€";
    }
  
}
