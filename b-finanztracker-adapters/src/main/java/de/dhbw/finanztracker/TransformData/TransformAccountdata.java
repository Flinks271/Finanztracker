package de.dhbw.finanztracker.TransformData;

import de.dhbw.finanztracker.domain.account.BankAccount;
import de.dhbw.finanztracker.domain.account.IAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransformAccountdata {

    public List<IAccount> TransformAccounts( List<Map<String, Object>> rows) {

        List<IAccount> accounts = new ArrayList<>();
        IAccount account = null;

        for (Map<String, Object> map : rows) {
            UUID accountId = UUID.fromString((String) map.get("accountId"));
            double balance = (double) map.get("balance");
            Integer counter = (Integer) map.get("counter");
            String accountName = (String) map.get("accountName");
            String bankName = (String) map.get("bankName");

            account = new BankAccount(accountId, balance, accountName, bankName, counter, new ArrayList<>());
            accounts.add(account);
        }

        return accounts;
    }

    public IAccount TransformAccount(Map<String, Object> map){
        IAccount account = null;

        UUID accountId = UUID.fromString((String) map.get("accountId"));
        double balance = (double) map.get("balance");
        Integer counter = (Integer) map.get("counter");
        String accountName = (String) map.get("accountName");
        String bankName = (String) map.get("bankName");

        account = new BankAccount(accountId, balance, accountName, bankName, counter, new ArrayList<>());
       
        return account;
    }

    public String generateInsertQuery(IAccount account, UUID userId) {
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
