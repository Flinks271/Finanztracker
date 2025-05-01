package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.List;
import java.util.Map;

public class AccountOverviewController {

    public static void Start(List<IRepository> repositories,IAccount account, TerminalUtility terminalUtility) {
        
        System.out.println("Starting the AccountOverview...");
        System.out.println("Loading accounts for user: " + account.getAccountName());
        IRepository accountRepository = repositories.get(0);
        //todo more repositorys for stuff like transactions, categories, etc.
        //List<Map<String, Object>> result = accountRepository.getWhere("user_id = '" + user.getUserId() + "'");
        

                
        Boolean shouldrun = true;
        do {
            terminalUtility.clearScreen();   

            /* 
            String whatNext = SingularAccountOverview.inputCommands(terminalUtility, user.getAccounts());

            if (whatNext.matches("\\d+")) { 
                int index = Integer.parseInt(whatNext);
                AccountOverviewController.Start(repositories, user.getAccount(index), terminalUtility);
            } else {
                switch (whatNext) {
                    case "create":
                    case "c":
                        CreateNewAccount.createAccount(accountRepository, user, terminalUtility);
                        break;
            
                    case "sign out":
                    case "s":
                        System.out.println("Signing out...");
                        shouldrun = false;
                        break;
                }
            }*/
        } while (shouldrun == true);
    }
    
}
