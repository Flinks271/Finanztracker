package de.dhbw.finanztracker.TransformData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.transaction.counterparty.Counterparty;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.ReaccuringOneDayAMonth;

public class TransformReaccuringdata {
    
    public static List<IReaccuring> TransformReaccurings( List<Map<String, Object>> rows) {

        List<IReaccuring> reaccurings = new ArrayList<>();

        for (Map<String, Object> map : rows) {
            reaccurings.add(TransformReaccuring(map));
        }

        return reaccurings;
    }

    public static IReaccuring TransformReaccuring(Map<String, Object> map) {

        UUID reaccuringID = (UUID) map.get("reaccuring_id");
        String name = (String) map.get("reaccuring_name");
        String description = (String) map.get("reaccuring_description");
        List<String> categories = null;
        double amount = ((BigDecimal) map.get("amount")).doubleValue();
        LocalDate startDate = (LocalDate) map.get("reaccuring_start_date");
        LocalDate lastModifiedDate = (LocalDate) map.get("reaccuring_last_modified_date");
        LocalDate endDate = (LocalDate) map.get("reaccuring_end_date");
        int intervalInDays = (int) map.get("interval_in_days");
        boolean active = endDate.isAfter(lastModifiedDate); 
        ICounterparty counterparty = new Counterparty((UUID)map.get("counterparty_id"), "", "");

        return new ReaccuringOneDayAMonth(
            reaccuringID,
            name,
            description,
            categories,
            amount,
            startDate,
            lastModifiedDate,
            endDate,
            intervalInDays,
            active,
            counterparty
        );
    }

    public static String generateInsertQuery(IReaccuring reaccuring, UUID bank_account_id, ICounterparty counterparty) {
        String query = "INSERT INTO reaccuring (reaccuring_id, bank_account_id, reaccuring_name, reaccuring_description, amount, reaccuring_start_date, reaccuring_last_modified_date, reaccuring_end_date, interval_in_days, counterparty_id) " +
                       "VALUES ({reaccuring_id}, {bank_account_id}, {reaccuring_name}, {reaccuring_description}, {amount}, {reaccuring_start_date}, {reaccuring_last_modified_date}, {reaccuring_end_date}, {interval_in_days}, {counterparty_id})";

        query = query.replace("{reaccuring_id}", "'" + reaccuring.getReaccuringId().toString() + "'");
        query = query.replace("{bank_account_id}", "'" + bank_account_id.toString() + "'");
        query = query.replace("{reaccuring_name}", "'" + reaccuring.getName() + "'");
        query = query.replace("{reaccuring_description}", "'" + reaccuring.getDescription() + "'");
        query = query.replace("{amount}", String.valueOf(reaccuring.getAmount()));
        query = query.replace("{reaccuring_start_date}", "'" + reaccuring.getStartDate().toString() + "'");
        query = query.replace("{reaccuring_last_modified_date}", "'" + reaccuring.getLastModifiedDate().toString() + "'");
        query = query.replace("{reaccuring_end_date}", reaccuring.getEndDate() != null ? "'" + reaccuring.getEndDate().toString() + "'" : "NULL");
        query = query.replace("{interval_in_days}", String.valueOf(reaccuring.getIntervalInDays()));
        query = query.replace("{counterparty_id}", "'" + counterparty.getCounterpartyId().toString() + "'");

        return query;
    }
}
