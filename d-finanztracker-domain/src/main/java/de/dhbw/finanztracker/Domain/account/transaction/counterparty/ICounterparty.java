package de.dhbw.finanztracker.domain.account.transaction.counterparty;

import java.util.UUID;

public interface ICounterparty {
    UUID getCounterpartyId();
    String getCounterpartyName();
    String getCounterPartyDescription();
    void setCounterpartyName(String name);
    void setCounterpartyDescription(String description);
}
