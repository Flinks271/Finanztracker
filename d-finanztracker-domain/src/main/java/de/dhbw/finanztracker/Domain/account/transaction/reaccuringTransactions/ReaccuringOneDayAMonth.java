package de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.Transaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public class ReaccuringOneDayAMonth implements IReaccuring {

    private UUID reacuringID;
    private String name;
    private String description;
    private List<String> categories;
    private double amount;
    private LocalDate startDate;
    private LocalDate lastModifiedDate;
    private LocalDate endDate;
    private int intervalInDays; 
    private boolean active;
    private ICounterparty counterparty;

    
    public ReaccuringOneDayAMonth(UUID reacuringID, String name, String description, List<String> categories, double amount,
                                  LocalDate startDate,LocalDate lastModifiedDate, LocalDate endDate, int intervalInDays, boolean active, ICounterparty counterparty) {
        this.reacuringID = reacuringID;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.amount = amount;
        this.startDate = startDate;
        this.lastModifiedDate = lastModifiedDate;
        this.endDate = endDate;
        this.intervalInDays = intervalInDays; 
        this.active = active;
        this.counterparty = counterparty;
    }

    public ReaccuringOneDayAMonth( String name, String description, List<String> categories, double amount,
                                  LocalDate startDate,LocalDate lastModifiedDate, LocalDate endDate, int intervalInDays, boolean active, ICounterparty counterparty) {
        this.counterparty = counterparty;
        this.reacuringID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.amount = amount;
        this.startDate = startDate;
        this.lastModifiedDate = lastModifiedDate;
        this.endDate = endDate;
        this.intervalInDays = intervalInDays; 
        this.active = active;
    }

    @Override
    public UUID getReaccuringId() {
        return reacuringID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<String> getCategorys() {
        return categories;
    }

    @Override
    public void setCategorys(List<String> categories) {
        this.categories = categories;
    }
    
    @Override
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public ICounterparty getCounterparty() {
        return counterparty;
    }

    @Override
    public void setCounterparty(ICounterparty counterparty) {
        this.counterparty = counterparty;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String getCategoriesString() {
        return String.join(", ", categories);
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate != null ? LocalDate.parse(endDate) : null;
    }

    @Override
    public int getIntervalInDays() {
        return intervalInDays;
    }

    @Override
    public void setIntervalInDays(int intervalInDays) {
        this.intervalInDays = intervalInDays;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate != null ? LocalDate.parse(lastModifiedDate) : null;
    }

    public boolean isDueOnDate(LocalDate date) {
        if (!active || date.isBefore(startDate) || (endDate != null && date.isAfter(endDate))) {
            return false;
        }
        return date.getDayOfMonth() == intervalInDays;
    }

    public LocalDate getNextExecutionDate(LocalDate fromDate) {
        LocalDate nextDate = fromDate.withDayOfMonth(startDate.getDayOfMonth());
        if (nextDate.isBefore(fromDate)) {
            nextDate = nextDate.plusMonths(1);
        }
        return nextDate;
    }

    public List<ITransaction> updateTransactions(IAccount account) {
        LocalDate currentDate = lastModifiedDate != null ? lastModifiedDate.plusDays(1) : startDate;
        LocalDate today = LocalDate.now();
        List<ITransaction> transactions = new ArrayList<>();

        while (!currentDate.isAfter(today)) {
            if (isDueOnDate(currentDate)) {
                ITransaction transaction = new Transaction(
                                                UUID.randomUUID(),
                                                amount,
                                                description,
                                                currentDate,
                                                categories,
                                                counterparty
                                            );
                account.addTransaction(transaction);
                transactions.add(transaction);

            }
            currentDate = currentDate.plusMonths(1).withDayOfMonth(startDate.getDayOfMonth());
        }

        lastModifiedDate = today;
        return transactions;
    }
}