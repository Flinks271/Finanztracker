package de.dhbw.finanztracker.ui.accountInteractions;

import java.util.List;
import java.util.Map;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.ui.TerminalUtility;

public class SingularAccountOverview {

    public static void showAccountdetails(IAccount account, Map<String, IRepository> repositories, TerminalUtility terminalUtility) {

        List<IReaccuring> reaccuringTransactions = account.getReaccuringTransactions();
        List<ITransaction> transactionHistory = account.getTransactionHistory();    
            
        int maxIndexWidth = 2;
        maxIndexWidth = Math.max(maxIndexWidth, String.valueOf(reaccuringTransactions.size()).length() + 1);
        maxIndexWidth = Math.max(maxIndexWidth, String.valueOf(transactionHistory.size()).length() + 1);
        int maxNameWidth = account.getAccountName().length();
        maxNameWidth = Math.max(maxNameWidth, 12);
        int maxBankNameWidth = account.getBankName().length();
        maxBankNameWidth = Math.max(maxBankNameWidth, 9);
        int maxBalanceWidth = Math.max(String.valueOf(account.getBalance()).length(), 7);
        List<Integer> distanceAccountInfo = List.of(maxIndexWidth + 1, maxNameWidth + 2, maxBankNameWidth + 2, maxBalanceWidth + 3);
    
        System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + "s %n",
                "Nr", "Account Name","Bank Name", "Balance");
        System.out.println(terminalUtility.generateHyphenLineOneSide(distanceAccountInfo));
    
        System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + ".2f € %n",
                 "1", account.getAccountName(), account.getBankName(), account.getBalance());

        int repetitions = reaccuringTransactions.size();
        int nameWidth = reaccuringTransactions.stream()
                .map(a -> a.getName().length())
                .max(Integer::compare)
                .orElse(0);
        nameWidth = Math.max(nameWidth, 4);
        int amountWidth = reaccuringTransactions.stream()
                .map(a -> String.valueOf(a.getAmount()).length())
                .max(Integer::compare)
                .orElse(0);
        amountWidth = Math.max(amountWidth, 6);
        int counterpartyWidth = reaccuringTransactions.stream()
                .map(a -> a.getCounterparty().getCounterpartyName().length())
                .max(Integer::compare)
                .orElse(0);
        counterpartyWidth = Math.max(counterpartyWidth, 17);
        int dateWidth = reaccuringTransactions.stream()
                .map(a -> a.getStartDate().toString().length())
                .max(Integer::compare)
                .orElse(0);
        dateWidth = Math.max(dateWidth, 4);
        int categoriesWidth = reaccuringTransactions.stream()
                .map(a -> a.getCategoriesString().toString().length())
                .max(Integer::compare)
                .orElse(0);
        List<Integer> distanceReaccuring = List.of(maxIndexWidth + 1, nameWidth + 2, amountWidth + 2, counterpartyWidth + 2, dateWidth + 2, dateWidth + 2, categoriesWidth + 1);

        terminalUtility.generateHyphenLineTwoSides(distanceAccountInfo, distanceReaccuring);

        if (repetitions == 0) {
            terminalUtility.generateHyphenLineOneSideTop(distanceAccountInfo);
            System.out.println("No reaccuring transactions found for this account.");
        } else {
            System.out.printf("%-" + maxIndexWidth + "s | %-" + nameWidth + "s | %-" + amountWidth + ".2f € | %-" + counterpartyWidth + "s | %-" + dateWidth  + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                "Nr", "Name", "Amount", "Counterparty", "Start Date", "End Date", "Categories");
            System.out.println(terminalUtility.generateHyphenLineTwoSides(distanceAccountInfo, distanceReaccuring));
            for (int i = 0; i < repetitions; i++) {
                IReaccuring reaccuring = reaccuringTransactions.get(i);
                System.out.printf("%-" + maxIndexWidth + "s | %-" + nameWidth + "s | %-" + amountWidth + ".2f € | %-" + counterpartyWidth + "s | %-" + dateWidth  + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                    i + 1, reaccuring.getName(), reaccuring.getAmount(), reaccuring.getCounterparty().getCounterpartyName(),
                    reaccuring.getStartDate(), reaccuring.getEndDate(), reaccuring.getCategoriesString());
            } 
        }
        
        List<ITransaction> transactions = account.getTransactionHistory();
        repetitions = transactions.size();
        nameWidth = Math.max(nameWidth, transactions.stream()
                                        .map(a -> a.getDescription().length())
                                        .max(Integer::compare)
                                        .orElse(0));
        amountWidth = Math.max(amountWidth, transactions.stream()
                                        .map(a -> String.valueOf(a.getAmount()).length())
                                        .max(Integer::compare)
                                        .orElse(0));
        counterpartyWidth = Math.max(counterpartyWidth, transactions.stream()
                                        .map(a -> a.getCounterparty().getCounterpartyName().length())
                                        .max(Integer::compare)
                                        .orElse(0));

        List<Integer> distanceTransaction = List.of(maxIndexWidth + 1, nameWidth + 2, amountWidth + 2, counterpartyWidth + 2, dateWidth + 2, categoriesWidth + 1);
        terminalUtility.generateHyphenLineTwoSides(distanceReaccuring, distanceTransaction);

        if (repetitions == 0) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.printf("%-" + maxIndexWidth + "s | %-" + nameWidth + "s | %-" + amountWidth + ".2f € | %-" + counterpartyWidth + "s | %-" + dateWidth  + "s | %-" + categoriesWidth + "s %n",
                "Nr", "Description", "Amount", "Counterparty", "Date", "Categories");
            System.out.println(terminalUtility.generateHyphenLineOneSide(distanceTransaction));
            for (int i = 0; i < repetitions; i++) {
                IReaccuring reaccuring = reaccuringTransactions.get(i);
                System.out.printf("%-" + maxIndexWidth + "s | %-" + nameWidth + "s | %-" + amountWidth + ".2f € | %-" + counterpartyWidth + "s | %-" + dateWidth  + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                    i + 1, reaccuring.getName(), reaccuring.getAmount(), reaccuring.getCounterparty().getCounterpartyName(),
                    reaccuring.getStartDate(), reaccuring.getEndDate(), reaccuring.getCategoriesString());
            } 
        }       
    }
    
}
