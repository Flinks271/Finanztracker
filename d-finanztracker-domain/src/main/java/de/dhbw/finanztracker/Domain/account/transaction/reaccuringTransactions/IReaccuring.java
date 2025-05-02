package de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    void setActive(boolean active);
    void setName(String name);
    void setDescription(String description);
    void setCategorys(List<String> categorys);
    void setAmount(double amount);
    void setStartDate(String startDate);
    void setLastModifiedDate(String lastModifiedDate);
    void setEndDate(String endDate);
    void setIntervalInDays(int intervalInDays);
    
}
