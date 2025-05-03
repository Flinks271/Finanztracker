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
        List<Integer> distanceBefore = List.copyOf(distanceAccountInfo);
    
        System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + "s %n",
                "Nr", "Account Name","Bank Name", "Balance");
        System.out.println(terminalUtility.generateHyphenLineOneSide(distanceAccountInfo));
    
        System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxBankNameWidth + "s | %-" + maxBalanceWidth + ".2f € %n",
                 "1", account.getAccountName(), account.getBankName(), account.getBalance());

        maxIndexWidth = Math.max(2, String.valueOf(reaccuringTransactions.size()).length());

        int nameWidth = Math.max(4, reaccuringTransactions.stream()
                .map(a -> a.getName().length())
                .max(Integer::compare)
                .orElse(0));
        int amountWidth = Math.max(6, reaccuringTransactions.stream()
                .map(a -> String.format("%.2f", a.getAmount()).length() + 2)
                .max(Integer::compare)
                .orElse(0));
        int counterpartyWidth = Math.max(17, reaccuringTransactions.stream()
                .map(a -> a.getCounterparty().getCounterpartyName().length())
                .max(Integer::compare)
                .orElse(0));
        int dateWidth = Math.max(10, reaccuringTransactions.stream()
                .map(a -> a.getStartDate().toString().length())
                .max(Integer::compare)
                .orElse(0));
        int categoriesWidth = Math.max(10, reaccuringTransactions.stream()
                .map(a -> a.getCategoriesString().length())
                .max(Integer::compare)
                .orElse(0));
        
        if (!(reaccuringTransactions.isEmpty())) {    

            

            List<Integer> distanceReaccuring = List.of(maxIndexWidth + 1, nameWidth + 2, amountWidth + 2, counterpartyWidth + 2, dateWidth + 2, dateWidth + 2, categoriesWidth + 2);
            terminalUtility.generateHyphenLineTwoSides(distanceBefore, distanceReaccuring);
            distanceBefore = List.copyOf(distanceReaccuring);
            
            System.out.printf("%-" + maxIndexWidth + "s | %-" + nameWidth + "s | %-" + amountWidth + "s | %-" + counterpartyWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                    "Nr", "Name", "Amount", "Counterparty", "Start Date", "End Date", "Categories");
            System.out.println(terminalUtility.generateHyphenLineOneSide(distanceReaccuring));

            for (int i = 0; i < reaccuringTransactions.size(); i++) {
                IReaccuring reaccuring = reaccuringTransactions.get(i);
                System.out.printf("%-" + maxIndexWidth + "d | %-" + nameWidth + "s | %-" + (amountWidth - 2) + ".2f € | %-" + counterpartyWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                        i + 1,
                        reaccuring.getName() != null ? reaccuring.getName() : "N/A",
                        reaccuring.getAmount(),
                        reaccuring.getCounterparty() != null ? reaccuring.getCounterparty().getCounterpartyName() : "N/A",
                        reaccuring.getStartDate() != null ? reaccuring.getStartDate() : "N/A",
                        reaccuring.getEndDate() != null ? reaccuring.getEndDate() : "N/A",
                        reaccuring.getCategoriesString() != null ? reaccuring.getCategoriesString() : "N/A");
            }
        }

        if (!(transactionHistory.isEmpty())) {
            nameWidth = Math.max(nameWidth, transactionHistory.stream()
                    .map(a -> a.getDescription().length())
                    .max(Integer::compare)
                    .orElse(0));
            amountWidth = Math.max(amountWidth, transactionHistory.stream()
                    .map(a -> String.format("%.2f", a.getAmount()).length() + 2)
                    .max(Integer::compare)
                    .orElse(0));
            counterpartyWidth = Math.max(counterpartyWidth, transactionHistory.stream()
                    .map(a -> a.getCounterparty().getCounterpartyName().length())
                    .max(Integer::compare)
                    .orElse(0));
            
            List<Integer> distanceTransaction = List.of(maxIndexWidth + 1, nameWidth + 2, amountWidth + 2, counterpartyWidth + 2, dateWidth + 2, categoriesWidth + 2);
            System.out.println(terminalUtility.generateHyphenLineTwoSides(distanceBefore, distanceTransaction));

            System.out.printf("%-" + maxIndexWidth + "s | %-" + nameWidth + "s | %-" + amountWidth + "s | %-" + counterpartyWidth + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                    "Nr", "Name", "Amount", "Counterparty", "Date", "Categories");
            System.out.println(terminalUtility.generateHyphenLineOneSide(distanceTransaction));

            for (int i = 0; i < transactionHistory.size(); i++) {
                ITransaction transaction = transactionHistory.get(i);
                System.out.printf("%-" + maxIndexWidth + "d | %-" + nameWidth + "s | %-" + (amountWidth - 2) + ".2f € | %-" + counterpartyWidth + "s | %-" + dateWidth + "s | %-" + categoriesWidth + "s %n",
                        i + 1,
                        transaction.getDescription() != null ? transaction.getDescription() : "N/A",
                        transaction.getAmount(),
                        transaction.getCounterparty() != null ? transaction.getCounterparty().getCounterpartyName() : "N/A",
                        transaction.getExecutionDate() != null ? transaction.getExecutionDate() : "N/A",
                        transaction.getCategoriesString() != null ? transaction.getCategoriesString() : "N/A");
            }
        }
               
    }

    public static String inputCommands(TerminalUtility terminalUtility){
        System.out.println("Please select the Action you wish to do:");
        String command = null;

        String input = terminalUtility.readLine();
        char firstChar = input.isEmpty() ? '\0' : input.trim().charAt(0);
        
        switch (firstChar) {
            case 'c':
                input = input.substring(1).trim();
                char next = input.isEmpty() ? '\0' : input.charAt(0);
                switch (next) {
                    case 'r':
                        command = "cr";
                        break;
                    case 't':
                        command = "ct";
                        break;               
                    default:
                        System.out.println("Invalid input. Please try again.");
                        System.out.println("For help with the commands you may use 'h' or 'help'.");
                        return inputCommands(terminalUtility);
                }
                break;
            
            case 'd':
                String inputWithoutFirstChar = input.substring(1).trim();
                next = inputWithoutFirstChar.isEmpty() ? '\0' : inputWithoutFirstChar.charAt(0);
                String inputWithoutSecondChar = inputWithoutFirstChar.substring(1).trim();
                switch (next) {
                    case 'r':
                        if (inputWithoutSecondChar.matches("\\d+")) { 
                            command = "dr" + (Integer.parseInt(inputWithoutSecondChar) - 1);
                        } else {
                            System.out.println("Invalid input. Please try again.");
                            System.out.println("For help with the commands you may use 'h' or 'help'.");
                            return inputCommands(terminalUtility);
                        }
                        break;
                    case 't':
                        if (inputWithoutSecondChar.matches("\\d+")) { 
                            command = "dt" + (Integer.parseInt(inputWithoutSecondChar) - 1);
                        } else {
                            System.out.println("Invalid input. Please try again.");
                            System.out.println("For help with the commands you may use 'h' or 'help'.");
                            return inputCommands(terminalUtility);
                        }
                        break;               
                        default:
                            System.out.println("Invalid input. Please try again.");
                            System.out.println("For help with the commands you may use 'h' or 'help'.");
                            return inputCommands(terminalUtility);
                }
                break;
            case 'r':
                String whatNext = input.substring(1).trim();
                if (whatNext.matches("\\d+")) { 
                    command = "r" + (Integer.parseInt(whatNext) - 1);
                } else {
                    System.out.println("Invalid input. Please try again.");
                    System.out.println("For help with the commands you may use 'h' or 'help'.");
                    return inputCommands(terminalUtility);
                }
                break;
            case 't':
                String whatNext2 = input.substring(1).trim();
                if (whatNext2.matches("\\d+")) { 
                    command = "t" + (Integer.parseInt(whatNext2) - 1);
                } else {
                    System.out.println("Invalid input. Please try again.");
                    System.out.println("For help with the commands you may use 'h' or 'help'.");
                    return inputCommands(terminalUtility);
                }
                break;
            case 'e':
                command = "e";
                break;
            case 'u':
                command = "u";
                break;
            case 'y':
                command = "y";
                break;
    
            case 'h':
                System.out.println("Available commands:");
                System.out.println("c - create a new transaction or reaccuring transaction");
                System.out.println("c - must be followed by the first letter of the type you want to create: r for reaccuring transaction or t for transaction");
                System.out.println("r - select a reaccuring transaction by number to edit it");
                System.out.println("t - select a transaction by number to edit it");
                System.out.println("d - delete a transaction or reaccuring transaction");
                System.out.println("d - must be followed by the first letter of the type you want to delete: r for reaccuring transaction or t for transaction");
                System.out.println("d - must be followed by the number of the transaction or reaccuring transaction you want to delete");
                System.out.println("u - update the account information");
                System.out.println("y - delete the account");
                System.out.println("e - exit out of the account overview"); 
                System.out.println("h - show this help message");               
                return inputCommands(terminalUtility);
    
            default:
                System.out.println("Invalid input. Please try again.");
                System.out.println("For help with the commands you may use 'h' or 'help'.");
                return inputCommands(terminalUtility);
        }
        

        return command;
    }
    
}
