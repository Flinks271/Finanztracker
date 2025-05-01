package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformAccountdata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.ui.accountInteractions.AccountsOverview;
import de.dhbw.finanztracker.ui.accountInteractions.CreateNewAccount;

import java.util.List;
import java.util.Map;

public class AccountsOverviewController {

    public static void Start(List<IRepository> repositories, User user, TerminalUtility terminalUtility) {
        System.out.println("Starting the AccountOverview...");
        System.out.println("Loading accounts for user: " + user.getUsername());
        IRepository accountRepository = repositories.get(0);
        List<Map<String, Object>> result = accountRepository.getWhere("user_id = '" + user.getUserId() + "'");
        System.out.println("Result: " + result);

        if (result.isEmpty()) {
            CreateNewAccount.createAccount(accountRepository, user, terminalUtility);
        } else {
            List<IAccount> accounts = TransformAccountdata.TransformAccounts(result);
            user.addAccounts(accounts);
        }

        System.out.println("Loading accounts for user: " + user.getUsername());

        AccountsOverview.showAccounts(user, repositories, terminalUtility);        
        
    }
    
}
