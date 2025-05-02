package de.dhbw.finanztracker.TransformData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.Counterparty;

public class TransformCounterpartydata {

    public static List<ICounterparty> TransformCounterparties(List<Map<String, Object>> rows) {
        List<ICounterparty> counterparties = new ArrayList<>();

        for (Map<String, Object> map : rows) {
            counterparties.add(TransformCounterparty(map));
        }

        return counterparties;
    }

    public static ICounterparty TransformCounterparty(Map<String, Object> map) {
        Validate.isTrue(map.containsKey("counterparty_id"), "Missing required key: counterparty_id");
        Validate.isTrue(map.containsKey("counterparty_name"), "Missing required key: counterparty_name");
        Validate.isTrue(map.containsKey("counterparty_description"), "Missing required key: counterparty_description");

        UUID counterpartyID = (UUID) map.get("counterparty_id");
        String name = (String) map.get("counterparty_name");
        String description = (String) map.get("counterparty_description");

        return new Counterparty(counterpartyID, name, description);
    }

    public static String generateInsertQuery(ICounterparty counterparty) {
        String query = "INSERT INTO counterparty (counterparty_id, counterparty_name, counterparty_description) " +
                       "VALUES ({counterparty_id}, {counterparty_name}, {counterparty_description})";

        query = query.replace("{counterparty_id}", "'" + counterparty.getCounterpartyId().toString() + "'");
        query = query.replace("{counterparty_name}", "'" + counterparty.getCounterpartyName() + "'");
        query = query.replace("{counterparty_description}", "'" + counterparty.getCounterPartyDescription() + "'");

        return query;
    }
}