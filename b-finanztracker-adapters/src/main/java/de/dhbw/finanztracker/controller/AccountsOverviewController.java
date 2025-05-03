package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformAccountdata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.ui.accountInteractions.AccountsOverview;
import de.dhbw.finanztracker.ui.accountInteractions.CreateNewAccount;
import de.dhbw.finanztracker.ui.userInteractions.RemoveUser;

import java.util.List;
import java.util.Map;

public class AccountsOverviewController {

    public static void Start(Map<String, IRepository> repositories,List<User> users, User user, TerminalUtility terminalUtility) {

        System.out.println("Starting the AccountOverview...");
        System.out.println("Loading accounts for user: " + user.getUsername());
        IRepository accountRepository = repositories.get("accountRepository");
        List<Map<String, Object>> result = accountRepository.getCond("WHERE user_id = '" + user.getUserId() + "'");
        System.out.println("Result: " + result);

        if (result.isEmpty()) {
            CreateNewAccount.createAccount(accountRepository, user, terminalUtility);
        } else {
            List<IAccount> accounts = TransformAccountdata.TransformAccounts(result);
            user.addAccounts(accounts);
        }

        System.out.println("Loading accounts for user: " + user.getUsername());

        
        Boolean shouldrun = true;
        do {
            terminalUtility.clearScreen();
            if (user.getAccounts().isEmpty()) {
                CreateNewAccount.createAccount(accountRepository, user, terminalUtility);
            }
            AccountsOverview.showAccounts(user, repositories, terminalUtility);        

            String whatNext = AccountsOverview.inputCommands(terminalUtility, user.getAccounts());

            if (whatNext.matches("\\d+")) { 
                int index = Integer.parseInt(whatNext) - 1;
                AccountOverviewController.Start(repositories, user.getAccount(index), terminalUtility, user);
            } else {
                switch (whatNext) {
                    case "create":
                    case "c":
                        CreateNewAccount.createAccount(accountRepository, user, terminalUtility);
                        break;
                    case "remove":
                    case "r":
                        RemoveUser.removeAccount(repositories.get("userRepository"), users, user, terminalUtility);
                        shouldrun = false;
                        break;
                    case "sign out":
                    case "s":
                        System.out.println("Signing out...");
                        shouldrun = false;
                        break;
                }
            }
        } while (shouldrun == true);

        
        
    }
    
}
