package de.dhbw.finanztracker.transaction_use_cases;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.Transaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;

public class TransactionManagement {

    public static void deleteTransaction(IRepository repository, IAccount account, ITransaction transaction) {
        repository.deleteById(transaction.getTransactionId());
        account.removeTransaction(transaction);
    }

    public static ITransaction createTransaction(Map<String, Object> transactionData, IRepository repository, IAccount account, ICounterparty counterparty, List<String> categories) {
        ITransaction transaction = new Transaction(
            UUID.randomUUID(),
            Double.parseDouble((String) transactionData.get("amount")),
            (String) transactionData.get("description"),
            (LocalDate) transactionData.get("execution_date"),
            categories,
            counterparty
        );

        

        String query = "INSERT INTO transactions (transaction_id, bank_account_id, amount, transaction_description, execution_date, entry_date, last_modified_date, counterparty_id) VALUES " +
                       "('" + transaction.getTransactionId() + "', '" + account.getAccountId() + "', '" + transaction.getAmount() + "', '" + transaction.getDescription() + "', '" +
                       transaction.getExecutionDate() + "', '" + transaction.getEntryDate() + "', '" + transaction.getLastModifiedDate() + "', '" + transaction.getCounterparty().getCounterpartyId() + "')";
        System.err.println(query);
        repository.save(query);

        return transaction;
    }

    public static void updateTransaction(IRepository repository, ITransaction transaction, Map<String, Object> inputs, ICounterparty counterparty, List<String> categories, IAccount account) {
        account.setBalance(account.getBalance() - transaction.getAmount());
        
        transaction.setAmount(Double.parseDouble((String) inputs.get("amount")));
        transaction.setDescription((String) inputs.get("description"));
        transaction.setExecutionDate((LocalDate) inputs.get("execution_date"));
        transaction.setCounterparty(counterparty);
        transaction.setCategories(categories);
        transaction.setLastModifiedDate(LocalDate.now());

        String query = "UPDATE transactions SET " +
                       "amount = '" + transaction.getAmount() + "', " +
                       "transaction_description = '" + transaction.getDescription() + "', " +
                       "execution_date = '" + transaction.getExecutionDate() + "', " +
                       "last_modified_date = '" + transaction.getLastModifiedDate() + "', " +
                       "counterparty_id = '" + transaction.getCounterparty().getCounterpartyId() + "' " +
                       "WHERE transaction_id = '" + transaction.getTransactionId() + "'";
        System.err.println(query);
        repository.save(query);
        account.setBalance(account.getBalance() - transaction.getAmount());
    }
}