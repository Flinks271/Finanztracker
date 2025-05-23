package de.dhbw.finanztracker.domain.user;


import org.apache.commons.lang3.Validate;
import de.dhbw.finanztracker.domain.account.IAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final String username;
    private List<IAccount> accounts;

    public User(String username) {
        Validate.notNull(username, "Username must not be null");

        this.userId = UUID.randomUUID();
        this.username = username;
        this.accounts = new ArrayList<IAccount>();
    }

    public User(UUID userid, String username) {
        Validate.notNull(username, "Username must not be null");
        Validate.notNull(userid, "userid must not be null");

        this.userId = userid;
        this.username = username;
        this.accounts = new ArrayList<IAccount>();
    }
    
    public User(UUID userId,String username, List<IAccount> accounts) {
        Validate.notNull(username, "Username must not be null");
        Validate.notNull(userId, "userId must not be null");
        Validate.notNull(accounts, "Accounts must not be null");

        this.userId = userId;
        this.username = username;
        this.accounts = accounts;
    }

    public UUID getUserId() { 
        return userId; 
    }

    public String getUsername() { 
        return username; 
    }

    public List<IAccount> getAccounts() { 
        return accounts; 
    }

    public IAccount getAccount(int index){
        return accounts.get(index);
    }

    public void setAccounts(List<IAccount> accounts) {
        Validate.notNull(accounts, "Accounts must not be null");
        this.accounts = accounts;
    }

    public Boolean addAccounts (List<IAccount> accounts) {
        Validate.notNull(accounts, "Accounts must not be null");
        return this.accounts.addAll(accounts);
    }

    public Boolean addAccount(IAccount account) {
        Validate.notNull(account, "Account must not be null");
        return accounts.add(account);
    }
    public Boolean removeAccount(IAccount account) {
        Validate.notNull(account, "Account must not be null");
        return accounts.remove(account);
    }
}
