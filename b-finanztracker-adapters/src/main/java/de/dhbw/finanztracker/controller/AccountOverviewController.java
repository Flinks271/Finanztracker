package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformCounterpartydata;
import de.dhbw.finanztracker.TransformData.TransformReaccuringdata;
import de.dhbw.finanztracker.TransformData.TransformTransactiondata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.ui.accountInteractions.AccountsOverview;
import de.dhbw.finanztracker.ui.accountInteractions.SingularAccountOverview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AccountOverviewController {

    public static void Start(Map<String, IRepository> repositories,IAccount account, TerminalUtility terminalUtility) {
        
        System.out.println("Starting the AccountOverview...");
        System.out.println("Loading accounts for user: " + account.getAccountName());
        

        loadAcountData(account, repositories);
                
        Boolean shouldrun = true;
        do {   
            terminalUtility.clearScreen();
            SingularAccountOverview.showAccountdetails(account, repositories, terminalUtility);
            terminalUtility.readLine();

            String whatNext = SingularAccountOverview.inputCommands(terminalUtility);

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

    private static void loadAcountData(IAccount account, Map<String, IRepository> repositories) {
        IRepository transactionRepository = repositories.get("transactionRepository");
        IRepository reaccuringRepository = repositories.get("reaccuringRepository");
        IRepository categoriesRepository = repositories.get("categoriesRepository");
        IRepository counterpartyRepository = repositories.get("counterpartyRepository");
       
        List<Map<String, Object>> result = transactionRepository.getCond("WHERE bank_account_id = '" + account.getAccountId() + "'");
        List<ITransaction> transactions = TransformTransactiondata.TransformTransactions(result);
        for (ITransaction transaction : transactions) {
            List<String> categories = new ArrayList<>();
            UUID id = transaction.getTransactionId();
            List<Map<String, Object>> counterList = counterpartyRepository.
                                                getCond("WHERE counterparty_id = '" + transaction.getCounterparty().getCounterpartyId() + "'");
            ICounterparty counterparty = TransformCounterpartydata.TransformCounterparty(counterList.get(0));
            transaction.setCounterparty(counterparty);
            List<Map<String, Object>> categoryList =    categoriesRepository.
                                                        getCond("JOIN relation_transaction_category rtc ON trc.transaction_id = '" + id + "'");
            for (Map<String,Object> category: categoryList) {
                categories.add((String)category.get("category_name"));
            }
            transaction.setCategories(categories);
        }
        account.setTransactions(transactions);

        result = reaccuringRepository.getCond("WHERE bank_account_id = '" + account.getAccountId() + "'");
        List<IReaccuring> reaccurings = TransformReaccuringdata.TransformReaccurings(result);
        for (IReaccuring reaccuring : reaccurings) {
            List<String> categories = new ArrayList<>();
            UUID id = reaccuring.getReaccuringId();
            List<Map<String, Object>> counterList = counterpartyRepository.
                                                getCond("WHERE counterparty_id = '" + reaccuring.getCounterparty().getCounterpartyId() + "'");
            ICounterparty counterparty = TransformCounterpartydata.TransformCounterparty(counterList.get(0));
            reaccuring.setCounterparty(counterparty);
            List<Map<String, Object>> categoryList =    categoriesRepository.
                                                        getCond("JOIN relation_reaccuring_category rtc ON trc.transaction_id = '" + id + "'");
            for (Map<String,Object> category: categoryList) {
                categories.add((String)category.get("category_name"));
            }
            reaccuring.setCategories(categories);
        }
        account.setReaccuring(reaccurings);
    }
    
}
