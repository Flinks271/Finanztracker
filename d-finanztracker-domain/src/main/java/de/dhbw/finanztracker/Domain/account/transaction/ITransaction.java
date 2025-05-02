package de.dhbw.finanztracker.domain.account.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITransaction {
    UUID getTransactionId();
    double getAmount();
    String getDescription();
    LocalDate getExecutionDate();
    List<String> getCategories();
    String getCounterparty();
    boolean isPersisted();
    LocalDate getEntryDate();
    LocalDate getLastModifiedDate();
    void setLastModifiedDate(LocalDate lastModifiedDate);
    void setPersisted(boolean isPersisted);
    void setCategories(List<String> categorys);
    void setCounterparty(String counterparty);
    void setAmount(double amount);
    void setDescription(String description);
    void setExecutionDate(LocalDate date);
    void addCategory(String category);
    void removeCategory(String category);
}
