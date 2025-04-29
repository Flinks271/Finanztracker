package de.dhbw.finanztracker.TransformData;

import de.dhbw.finanztracker.domain.account.BankAccount;
import de.dhbw.finanztracker.domain.account.IAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransformAccountdata {

    public List<IAccount> TransformAccounts(ResultSet resultSet){

        List<IAccount> accounts = new ArrayList<>();
        IAccount account = null;

        try {
            while (resultSet.isClosed() == false) {
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public IAccount TransformAccount(ResultSet resultSet){
        IAccount account = null;

        try {
            resultSet.next();
            UUID accountId = UUID.fromString(resultSet.getString("accountId"));
            UUID userId = UUID.fromString(resultSet.getString("userId"));
            double balance = resultSet.getDouble("balance");
            Integer counter = resultSet.getInt("counter");
            String accountName = resultSet.getString("accountName");
            String bankName = resultSet.getString("bankName");

            account = new BankAccount(accountId, userId, balance, accountName, bankName, counter, new ArrayList<>());
        
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public String generateInsertQuery(IAccount account) {
        String query = "INSERT INTO accounts (account_id, user_id, balance, account_name, bank_name) " +
                       "VALUES ({account_id}, {user_id}, {balance}, {account_name}, {bank_name})";

        query = query.replace("{account_id}", "'" + account.getAccountId().toString() + "'");
        query = query.replace("{user_id}", "'" + account.getUserId().toString() + "'");
        query = query.replace("{balance}", String.valueOf(account.getBalance()));
        query = query.replace("{account_name}", "'" + account.getAccountName() + "'");
        query = query.replace("{bank_name}", "'" + account.getBankName() + "'");
        return query;
    }


}
