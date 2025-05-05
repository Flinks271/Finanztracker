package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformCounterpartydata;
import de.dhbw.finanztracker.TransformData.TransformReaccuringdata;
import de.dhbw.finanztracker.TransformData.TransformTransactiondata;
import de.dhbw.finanztracker.account_use_cases.account_manipulation.AccountManagement;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.ui.accountInteractions.RemoveAccount;
import de.dhbw.finanztracker.ui.accountInteractions.SingularAccountOverview;
import de.dhbw.finanztracker.ui.accountInteractions.UpdateAccount;
import de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions.CreateNewMonthlyReaccuring;
import de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions.CreateNewTransaction;
import de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions.RemoveReaccuring;
import de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions.RemoveTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AccountOverviewController {

    public static void Start(Map<String, IRepository> repositories,IAccount account, TerminalUtility terminalUtility, User user) {
        
        System.out.println("Starting the AccountOverview...");
        System.out.println("Loading accounts for account: " + account.getAccountName());
        

        loadAcountData(account, repositories);
                
        Boolean shouldrun = true;
        do {   
            terminalUtility.clearScreen();
            SingularAccountOverview.showAccountdetails(account, repositories, terminalUtility);

            String whatNext = SingularAccountOverview.inputCommands(terminalUtility);

            switch (whatNext.charAt(0)) {
                case 'c':
                    switch (whatNext.charAt(1)) {
                        case 't':
                            CreateNewTransaction.createTransaction(repositories, account, terminalUtility);
                            break;
                        case 'r':
                            CreateNewMonthlyReaccuring.createReaccuring(repositories, account, terminalUtility);
                            break;
                        default:
                            break;
                    }
                    break;
                case 'r':
                    whatNext = whatNext.substring(1);
                    int index = Integer.parseInt(whatNext);
                    CreateNewMonthlyReaccuring.updateReaccuring(repositories, account.getReaccuringTransactions().get(index), terminalUtility);
                    break;
                case 't':
                    whatNext = whatNext.substring(1);
                    index = Integer.parseInt(whatNext);
                    CreateNewTransaction.updateTransaction(repositories, account.getTransactionHistory().get(index), terminalUtility, account);
                    break;
                case 'd':
                    switch (whatNext.charAt(1)) {
                        case 't':
                            RemoveTransaction.removeTransaction(repositories.get("transactionRepository"), account, account.getTransactionHistory().get(Integer.parseInt(whatNext.substring(2))), terminalUtility);
                            break;
                        case 'r':
                            RemoveReaccuring.removeReaccuring(repositories.get("reaccuringRepository"), account, account.getReaccuringTransactions().get(Integer.parseInt(whatNext.substring(2))), terminalUtility);
                            break;
                        default:
                            break;
                    }
                    break;
                case 'u':
                    UpdateAccount.updateAccount(repositories.get("accountRepository"), account, terminalUtility);
                    break;
                case 'y':
                    RemoveAccount.removeAccount(repositories.get("accountRepository"), user, account, terminalUtility);
                    break;
                case 'e':
                    shouldrun = false;
                    break;
            }
        } while (shouldrun == true);

        AccountManagement.updateAccount(repositories.get("accountRepository"), account);
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
                                                        getCond("JOIN relation_transaction_category rtc ON rtc.category_name = c.category_name WHERE rtc.transaction_id ='" + id + "'");
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
                                                        getCond("JOIN relation_reaccuring_category rrc ON rrc.category_name = c.category_name WHERE rrc.reaccuring_id ='" + id + "'");
            for (Map<String,Object> category: categoryList) {
                categories.add((String)category.get("category_name"));
            }
            reaccuring.setCategories(categories);

            List<ITransaction> addedTransactions = reaccuring.updateTransactions(account);
            System.out.println("Added transactions: " + addedTransactions.size());
            for (ITransaction addedTransaction : addedTransactions) {
                String query = "INSERT INTO transactions (transaction_id, bank_account_id, amount, transaction_description, execution_date, entry_date, last_modified_date, counterparty_id) VALUES " +
                       "('" + addedTransaction.getTransactionId() + "', '" + account.getAccountId() + "', '" + addedTransaction.getAmount() + "', '" + addedTransaction.getDescription() + "', '" +
                       addedTransaction.getExecutionDate() + "', '" + addedTransaction.getEntryDate() + "', '" + addedTransaction.getLastModifiedDate() + "', '" + addedTransaction.getCounterparty().getCounterpartyId() + "')";
                transactionRepository.save(query);
            }
        }
        account.setReaccuring(reaccurings);
    }
    
}
