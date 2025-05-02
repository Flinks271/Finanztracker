package de.dhbw.finanztracker.domain.account.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public interface ITransaction {
    UUID getTransactionId();
    double getAmount();
    String getDescription();
    LocalDate getExecutionDate();
    List<String> getCategories();
    ICounterparty getCounterparty();
    boolean isPersisted();
    LocalDate getEntryDate();
    LocalDate getLastModifiedDate();
    String getCategoriesString();
    void setLastModifiedDate(LocalDate lastModifiedDate);
    void setPersisted(boolean isPersisted);
    void setCategories(List<String> categorys);
    void setCounterparty(ICounterparty counterparty);
    void setAmount(double amount);
    void setDescription(String description);
    void setExecutionDate(LocalDate date);
    void addCategory(String category);
    void removeCategory(String category);
}
