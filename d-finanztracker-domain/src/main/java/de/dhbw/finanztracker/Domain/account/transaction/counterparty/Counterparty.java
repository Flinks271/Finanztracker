package de.dhbw.finanztracker.domain.account.transaction.counterparty;

import java.util.UUID;

public class Counterparty implements ICounterparty {
    private final UUID counterpartyId;
    private String counterpartyName;
    private String counterpartyDescription;

    public Counterparty(UUID counterpartyId, String counterpartyName, String counterpartyDescription) {
        this.counterpartyId = counterpartyId;
        this.counterpartyName = counterpartyName;
        this.counterpartyDescription = counterpartyDescription;
    }

    @Override
    public UUID getCounterpartyId() {
        return counterpartyId;
    }

    @Override
    public String getCounterpartyName() {
        return counterpartyName;
    }

    @Override
    public String getCounterPartyDescription() {
        return counterpartyDescription;
    }

    @Override
    public void setCounterpartyName(String name) {
        this.counterpartyName = name;
    }

    @Override
    public void setCounterpartyDescription(String description) {
        this.counterpartyDescription = description;
    }
    
}
