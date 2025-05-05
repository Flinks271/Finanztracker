package de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public interface IReaccuring {

    UUID getReaccuringId();
    String getName();
    String getDescription();
    List<String> getCategorys();
    double getAmount();
    LocalDate getStartDate();
    LocalDate getLastModifiedDate();
    LocalDate getEndDate();
    int getIntervalInDays();
    boolean isActive();
    ICounterparty getCounterparty();
    String getCategoriesString();
    void setCounterparty(ICounterparty counterparty);
    void setActive(boolean active);
    void setName(String name);
    void setDescription(String description);
    void setCategorys(List<String> categorys);
    void setAmount(double amount);
    void setStartDate(String startDate);
    void setLastModifiedDate(String lastModifiedDate);
    void setEndDate(String endDate);
    void setIntervalInDays(int intervalInDays);
    void setCategories(List<String> categories);
    List<ITransaction> updateTransactions(IAccount account);
    public List<ITransaction> updateTransactionsFirstTime(IAccount account);
    
}
