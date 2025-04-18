package de.dhbw.finanztracker.Domain.account.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITransaction {
    int getTransactionId();
    UUID getBankAccountId();
    LocalDate getDate();
    List<String> getCategories();
    String getCounterparty();
    boolean isRecurring();
    void setCategories(List<String> categorys);
    void setCounterparty(String counterparty);
    void setDate(LocalDate date);
    void addCategory(String category);
    void removeCategory(String category);
}
