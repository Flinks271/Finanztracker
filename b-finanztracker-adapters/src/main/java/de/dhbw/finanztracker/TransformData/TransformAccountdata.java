package de.dhbw.finanztracker.TransformData;

import de.dhbw.finanztracker.domain.account.BankAccount;
import de.dhbw.finanztracker.domain.account.IAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransformAccountdata {

    public static List<IAccount> TransformAccounts( List<Map<String, Object>> rows) {

        List<IAccount> accounts = new ArrayList<>();

        for (Map<String, Object> map : rows) {
            accounts.add(TransformAccount(map));
        }

        return accounts;
    }

    public static IAccount TransformAccount(Map<String, Object> map){
        IAccount account = null;

        UUID accountId = (UUID) map.get("account_id");
        double balance = ((BigDecimal)map.get("balance")).doubleValue();
        Integer counter = (Integer) map.get("counter");
        String accountName = (String) map.get("account_name");
        String bankName = (String) map.get("bank_name");

        account = new BankAccount(accountId, balance, accountName, bankName, counter, new ArrayList<>());
       
        return account;
    }

    public static String generateInsertQuery(IAccount account, UUID userId) {
        String query = "INSERT INTO accounts (account_id, user_id, balance, account_name, bank_name) " +
                       "VALUES ({account_id}, {user_id}, {balance}, {account_name}, {bank_name})";

        query = query.replace("{account_id}", "'" + account.getAccountId().toString() + "'");
        query = query.replace("{user_id}", "'" + userId.toString() + "'");
        query = query.replace("{balance}", String.valueOf(account.getBalance()));
        query = query.replace("{account_name}", "'" + account.getAccountName() + "'");
        query = query.replace("{bank_name}", "'" + account.getBankName() + "'");
        return query;
    }


}
