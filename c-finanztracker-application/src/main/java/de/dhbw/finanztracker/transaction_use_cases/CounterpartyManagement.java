package de.dhbw.finanztracker.transaction_use_cases;

import java.util.Map;
import java.util.UUID;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.Counterparty;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public class CounterpartyManagement {

    public static void deleteCounterparty(IRepository repository, ICounterparty counterparty) {
        repository.deleteById(counterparty.getCounterpartyId());
    }

    public static ICounterparty createCounterparty(Map<String, String> counterpartyData, IRepository repository) {
        ICounterparty counterparty = new Counterparty(
            UUID.randomUUID(),
            (String) counterpartyData.get("name"),
            (String) counterpartyData.get("description")
        );

        String query = "INSERT INTO counterparty (counterparty_id, counterparty_name, counterparty_description) VALUES " +
                       "('" + counterparty.getCounterpartyId() + "', '" + counterparty.getCounterpartyName() + "', '" + counterparty.getCounterPartyDescription() + "')";
        System.err.println(query);
        repository.save(query);

        return counterparty;
    }

    public static void updateCounterparty(IRepository repository, ICounterparty counterparty) {
        String query = "UPDATE counterparty SET " +
                       "counterparty_name = '" + counterparty.getCounterpartyName() + "', " +
                       "counterparty_description = '" + counterparty.getCounterPartyDescription() + "' " +
                       "WHERE counterparty_id = '" + counterparty.getCounterpartyId() + "'";
        
        System.err.println(query);
        repository.save(query);
    }
}