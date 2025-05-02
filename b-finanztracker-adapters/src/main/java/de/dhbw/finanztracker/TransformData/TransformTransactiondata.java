package de.dhbw.finanztracker.TransformData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.Transaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.Counterparty;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public class TransformTransactiondata {
    
    public static List<ITransaction> TransformTransactions( List<Map<String, Object>> rows) {

        List<ITransaction> transactions = new ArrayList<>();

        for (Map<String, Object> map : rows) {
            transactions.add(TransformTransaction(map));
        }

        return transactions;
    }

    public static ITransaction TransformTransaction(Map<String, Object> map) {

        ITransaction transaction = null;

        UUID transactionID = (UUID)map.get("transaction_id");
        double amount = ((BigDecimal)map.get("amount")).doubleValue();
        String description = (String)map.get("description");
        List<String> categories = null;
        LocalDate executionDateString = (LocalDate)map.get("execution_date");
        LocalDate entryDateString = (LocalDate)map.get("entry_date");
        LocalDate lastModifiedDateString = (LocalDate)map.get("last_modified_date");
        boolean isPersisted = true;
        ICounterparty counterparty = new Counterparty((UUID)map.get("counterparty_id"), "", "");


        transaction = new Transaction(transactionID, amount, description, executionDateString, entryDateString, lastModifiedDateString, categories, counterparty,  isPersisted);
       
        return transaction;
    }

    public static String generateInsertQuery(ITransaction transaction, UUID bank_account_id, ICounterparty counterparty) {
        String query = "INSERT INTO transactions (transaction_id, bank_account_id, amount, transaction_description, execution_date, entry_date, last_modified_date, counterparty_id) " +
                       "VALUES ({transaction_id}, {bank_account_id}, {amount}, {transaction_description}, {execution_date}, {entry_date}, {last_modified_date}, {counterparty_id})";

        query = query.replace("{transaction_id}", "'" + transaction.getTransactionId().toString() + "'");
        query = query.replace("{bank_account_id}", "'" + bank_account_id.toString() + "'");
        query = query.replace("{amount}", String.valueOf(transaction.getAmount()));
        query = query.replace("{transaction_description}", "'" + transaction.getDescription() + "'");
        query = query.replace("{execution_date}", "'" + transaction.getExecutionDate().toString() + "'");
        query = query.replace("{entry_date}", "'" + transaction.getEntryDate().toString() + "'");
        query = query.replace("{last_modified_date}", "'" + transaction.getLastModifiedDate().toString() + "'");
        query = query.replace("{counterparty_id}", "'" + counterparty.getCounterpartyId().toString() + "'");
        return query;
    }
}
