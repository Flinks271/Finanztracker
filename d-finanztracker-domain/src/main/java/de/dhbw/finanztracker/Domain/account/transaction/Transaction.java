package de.dhbw.finanztracker.domain.account.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public class Transaction implements ITransaction {
    private final UUID transactionId;
    private double amount;
    private String description;
    private LocalDate execution_date;
    private LocalDate entry_date;
    private LocalDate last_modified_date;
    private List<String> categories;
    private ICounterparty counterparty;
    private boolean isPersisted;
  
    public Transaction(UUID transactionID, double amount, String description, LocalDate executionDate,
        LocalDate entryDate, LocalDate lastModifiedDate, List<String> categories, ICounterparty counterparty, boolean isPersisted) {
    this.transactionId = transactionID;
    this.amount = amount;
    this.description = description;
    this.execution_date = executionDate;
    this.entry_date = entryDate;
    this.last_modified_date = lastModifiedDate;
    this.categories = categories;
    this.counterparty = counterparty;
    this.isPersisted = isPersisted;
}

public Transaction(UUID transactionID, double amount, String description, LocalDate executionDate,
        List<String> categories, ICounterparty counterparty) {
    this.transactionId = transactionID;
    this.amount = amount;
    this.description = description;
    this.execution_date = executionDate;
    this.entry_date = LocalDate.now();
    this.last_modified_date = LocalDate.now();
    this.categories = categories;
    this.counterparty = counterparty;
    this.isPersisted = false;
}


    @Override
    public UUID getTransactionId() {
        return transactionId;
    }


    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getExecutionDate() {
        return execution_date;
    }

    @Override
    public void setExecutionDate(LocalDate execution_date) {
        this.execution_date = execution_date;
    }

    @Override
    public List<String> getCategories() {
        return categories;
    }


    @Override
    public ICounterparty getCounterparty() {
        return counterparty;
    }

   
    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setCounterparty(ICounterparty counterparty) {
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

    @Override
    public boolean isPersisted() {
        return isPersisted;
    }

    @Override
    public void setPersisted(boolean isPersisted) {
        this.isPersisted = isPersisted;
    }

	@Override
	public LocalDate getEntryDate() {
		return entry_date;
	}

	@Override
	public LocalDate getLastModifiedDate() {
		return last_modified_date;
	}

	@Override
	public void setLastModifiedDate(LocalDate lastModifiedDate) {
		this.last_modified_date = lastModifiedDate;
	}

    
}
