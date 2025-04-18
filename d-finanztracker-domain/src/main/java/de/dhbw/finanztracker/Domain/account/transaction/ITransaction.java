package de.dhbw.finanztracker.domain.account.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITransaction {
    int getTransactionId();
    UUID getBankAccountId();
    double getAmount();
    String getDescription();
    LocalDate getDate();
    List<String> getCategories();
    String getCounterparty();
    boolean isRecurring();
    void setCategories(List<String> categorys);
    void setCounterparty(String counterparty);
    void setAmount(double amount);
    void setDescription(String description);
    void setDate(LocalDate date);
    void addCategory(String category);
    void removeCategory(String category);
}
