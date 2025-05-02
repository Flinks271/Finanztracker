package de.dhbw.finanztracker.TransformData;


import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;

public class TransformRelationReaccuringCategory {
    public static String generateInsertQuery(IReaccuring reaccuring, String categoryName) {
        String query = "INSERT INTO relation_reaccuring_category (transaction_id, category_name) " +
                       "VALUES ({transaction_id}, {category_name})";

        query = query.replace("{transaction_id}", "'" + reaccuring.getReaccuringId().toString() + "'");
        query = query.replace("{category_name}", "'" + categoryName + "'");

        return query;
    }
}
