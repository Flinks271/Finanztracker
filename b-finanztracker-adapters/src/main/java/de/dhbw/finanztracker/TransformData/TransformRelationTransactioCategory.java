package de.dhbw.finanztracker.TransformData;


import de.dhbw.finanztracker.domain.account.transaction.ITransaction;

public class TransformRelationTransactioCategory {
    public static String generateInsertQuery(ITransaction transaction, String categoryName) {
        String query = "INSERT INTO relation_transaction_category (transaction_id, category_name) " +
                       "VALUES ({transaction_id}, {category_name})";

        query = query.replace("{transaction_id}", "'" + transaction.getTransactionId().toString() + "'");
        query = query.replace("{category_name}", "'" + categoryName + "'");

        return query;
    }
}
