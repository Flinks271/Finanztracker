package de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions;

import de.dhbw.finanztracker.transaction_use_cases.TransactionManagement;
import de.dhbw.finanztracker.TransformData.TransformCounterpartydata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewTransaction {

    public static void createTransaction(Map<String, IRepository> repositorys, IAccount account, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();
        ITransaction transaction = null;
        IRepository repository = repositorys.get("transactionRepository");

        System.out.println("Creating a new transaction for account: " + account.getAccountName());

        Map<String, Object> inputs = getTransactionInputs(terminalUtility);
        ICounterparty counterparty = getCounterpartyInputs(terminalUtility, repositorys.get("counterpartyRepository"));
        List<String> categories = getCategoriesInputs(terminalUtility, repositorys.get("categoriesRepository"));

        transaction = TransactionManagement.createTransaction(inputs, repository, account, counterparty, categories);

        if (transaction != null) {
            System.out.println("Transaction " + transaction.getDescription() + " has been created successfully.");
            account.addTransaction(transaction);
        } else {
            System.out.println("No transaction was created.");
        }

        terminalUtility.pauseForOneSecond();
    }

    private static Map<String, Object> getTransactionInputs(TerminalUtility terminalUtility) {
        Map<String, Object> inputs = new HashMap<>();

        System.out.println("Please enter the transaction amount: ");
        String inputAmount;
        do {
            inputAmount = terminalUtility.readLine();
            if (!inputAmount.matches("^\\d+(\\.|,)?(\\d{1,2})?$")) {
                System.out.println("Invalid amount format. Please enter a valid double value with up to 2 decimal places.");
            }
        } while (!inputAmount.matches("^\\d+(\\.|,)?(\\d{1,2})?$"));
        inputs.put("amount", inputAmount.replace(",", "."));

        System.out.println("Please enter the transaction description: ");
        String inputDescription = terminalUtility.readLine();
        inputs.put("description", inputDescription);

        System.out.println("Please enter the execution date (YYYY-MM-DD): ");
        String inputDate;
        LocalDate executionDate = null;
        do {
            inputDate = terminalUtility.readLine();
            try {
                executionDate = LocalDate.parse(inputDate);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
            }
        } while (executionDate == null);
        inputs.put("execution_date", executionDate);

        return inputs;
    }

    private static ICounterparty getCounterpartyInputs(TerminalUtility terminalUtility, IRepository repository) {
        List<Map<String, Object>> result = repository.getAll();
        List<ICounterparty> counterpartyList = TransformCounterpartydata.TransformCounterparties(result);

        System.out.println("Please select a counterparty from the following list (or create a new one):");
        System.out.println("You select a counterparty by entering the number in front of the name.");
        System.out.println("If you want to create a new counterparty, please enter 'new'.");

        if (!(counterpartyList.isEmpty())) {
            System.out.println();
            int maxIndexWidth = String.valueOf(counterpartyList.size()).length();
            maxIndexWidth = Math.max(maxIndexWidth, 2);
            int maxNameWidth = counterpartyList.stream()
                    .map(a -> a.getCounterpartyName().length())
                    .max(Integer::compare)
                    .orElse(0);
            maxNameWidth = Math.max(maxNameWidth, 4);
            int maxDescriptionWidth = counterpartyList.stream()
                    .map(a -> a.getCounterPartyDescription().length())
                    .max(Integer::compare)
                    .orElse(0);
            maxDescriptionWidth = Math.max(maxDescriptionWidth, 11);
            
            System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxDescriptionWidth + "s %n",
                "Nr", "Name", "Description");
            terminalUtility.generateHyphenLineOneSide(List.of(maxIndexWidth + 1, maxNameWidth + 2, maxDescriptionWidth + 1));
    
            for (int i = 0; i < counterpartyList.size(); i++) {
                ICounterparty counterparty = counterpartyList.get(i);
                System.out.printf("%-" + maxIndexWidth + "s | %-" + maxNameWidth + "s | %-" + maxDescriptionWidth + "s %n",
                i + 1, counterparty.getCounterpartyName(), counterparty.getCounterPartyDescription());
            }
        
            System.out.println();
        }
        

        while (true) {
            System.out.println("Enter the number of the counterparty or type 'new' to create a new one:");
            String input = terminalUtility.readLine();

            if (input.equalsIgnoreCase("new")) {
                return CreateNewCounterparty.createCounterparty(repository, terminalUtility);
            }

            try {
                int selectedIndex = Integer.parseInt(input) - 1;
                if (selectedIndex >= 0 && selectedIndex < counterpartyList.size()) {
                    return counterpartyList.get(selectedIndex);
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'new'.");
            }
        }
        
    }

    private static List<String> getCategoriesInputs(TerminalUtility terminalUtility, IRepository repository) {
        List<String> categories = new ArrayList<>();
        System.out.println("Please enter the categories for this transaction (type 'done' to finish):");
        System.out.println("");


        while (true) {
            String category = terminalUtility.readLine();
            if (category.equalsIgnoreCase("done")) {
                break;
            }
            if (!categories.contains(category)) {
                categories.add(category);
            } else {
                System.out.println("Category already added. Please enter a different category.");
            }
        }

        return categories;
    }
}