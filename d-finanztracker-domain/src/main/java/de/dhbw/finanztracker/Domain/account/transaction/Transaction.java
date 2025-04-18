package de.dhbw.finanztracker.Domain.account.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Transaction implements ITransaction {
    private final int transactionId;
    private final UUID bankAccountId;
    private LocalDate date;
    private List<String> categories;
    private String counterparty;
    private final boolean isRecurring;
  
    public Transaction(int transactionID, UUID bankAccountId, LocalDate date,
        List<String> categories, String counterparty, boolean isRecurring) {
        this.transactionId = transactionID;
        this.bankAccountId = bankAccountId;
        this.date = date;
        this.categories = categories;
        this.counterparty = counterparty;
        this.isRecurring = true;       
    }

    public Transaction(int transactionID, UUID bankAccountId, LocalDate date,
        List<String> categories, String counterparty) {
        this.transactionId = transactionID;
        this.bankAccountId = bankAccountId;
        this.date = date;
        this.categories = categories;
        this.counterparty = counterparty;
        this.isRecurring = false;
    }


    @Override
    public int getTransactionId() {
        return transactionId;
    }

    @Override
    public UUID getBankAccountId() {
        return bankAccountId;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public List<String> getCategories() {
        return categories;
    }


    @Override
    public String getCounterparty() {
        return counterparty;
    }


    @Override
    public boolean isRecurring() {
        return isRecurring;
    }

    @Override
    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }
    
    @Override
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public void addCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }
    
    @Override
    public void removeCategory(String category) {
        categories.remove(category);
    }
    
}
