package de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions;

import de.dhbw.finanztracker.transaction_use_cases.ReaccuringManagement;
import de.dhbw.finanztracker.TransformData.TransformCounterpartydata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewMonthlyReaccuring {

    public static void createReaccuring(Map<String, IRepository> repositories, IAccount account, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();
        IReaccuring reaccuring = null;
        IRepository repository = repositories.get("reaccuringRepository");

        System.out.println("Creating a new monthly reaccuring transaction for account: " + account.getAccountName());

        Map<String, Object> inputs = getReaccuringInputs(terminalUtility);
        ICounterparty counterparty = getCounterpartyInputs(terminalUtility, repositories.get("counterpartyRepository"));
        List<String> categories = getCategoriesInputs(terminalUtility, repositories.get("categoriesRepository"));

        reaccuring = ReaccuringManagement.createReaccuring(inputs, repository, account, counterparty, categories);

        if (reaccuring != null) {
            System.out.println("Reaccuring transaction " + reaccuring.getName() + " has been created successfully.");
            account.addReaccuringTransaction(reaccuring);

            List<ITransaction> addedTransactions = reaccuring.updateTransactionsFirstTime(account);
            System.out.println("Added transactions: " + addedTransactions.size());
            for (ITransaction addedTransaction : addedTransactions) {
                String query = "INSERT INTO transactions (transaction_id, bank_account_id, amount, transaction_description, execution_date, entry_date, last_modified_date, counterparty_id) VALUES " +
                       "('" + addedTransaction.getTransactionId() + "', '" + account.getAccountId() + "', '" + addedTransaction.getAmount() + "', '" + addedTransaction.getDescription() + "', '" +
                       addedTransaction.getExecutionDate() + "', '" + addedTransaction.getEntryDate() + "', '" + addedTransaction.getLastModifiedDate() + "', '" + addedTransaction.getCounterparty().getCounterpartyId() + "')";
                repository.save(query);
            }
        } else {
            System.out.println("No reaccuring transaction was created.");
        }

        terminalUtility.pauseForOneSecond();
    }

    public static void updateReaccuring(Map<String, IRepository> repositorys, IReaccuring reaccuring, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();


        System.out.println("Ary you sure you want to update The Data in this transaction?:");
        System.out.println("y/n:");
        String input = terminalUtility.readLine();
        
        

        if (input.equals("y")) {
            Map<String, Object> inputs = getReaccuringInputs(terminalUtility);
            ICounterparty counterparty = getCounterpartyInputs(terminalUtility, repositorys.get("counterpartyRepository"));
            List<String> categories = getCategoriesInputs(terminalUtility, repositorys.get("categoriesRepository"));
            ReaccuringManagement.updateReaccuring(repositorys.get("transactionRepository"), reaccuring, inputs, counterparty, categories);
            System.out.println("Transaction: " + reaccuring.getDescription() + " has been updated successfully.");
        } else if (input.equals("n")) {
            System.out.println("Transaction update cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            updateReaccuring(repositorys, reaccuring, terminalUtility);   
        }
    }

    private static Map<String, Object> getReaccuringInputs(TerminalUtility terminalUtility) {
        Map<String, Object> inputs = new HashMap<>();

        System.out.println("Please enter the name of the reaccuring transaction: ");
        String inputName = "";
        do {
            inputName = terminalUtility.readLine();
            if (inputName == null || inputName.isEmpty()) {
                System.out.println("Reaccuring transaction name cannot be empty. Please try again.");
            }
        } while (inputName == null || inputName.isEmpty());
        inputs.put("name", inputName);

        System.out.println("Please enter the description of the reaccuring transaction: ");
        String inputDescription = "";
        do {
            inputDescription = terminalUtility.readLine();
            if (inputDescription == null || inputDescription.isEmpty()) {
                System.out.println("Reaccuring transaction description cannot be empty. Please try again.");
            }
        } while (inputDescription == null || inputDescription.isEmpty());
        inputs.put("description", inputDescription);

        System.out.println("Please enter the amount: ");
        String inputAmount;
        do {
            inputAmount = terminalUtility.readLine();
            if (!inputAmount.matches("^-?\\d+(\\.|,)?(\\d{1,2})?$")) {
                System.out.println("Invalid amount format. Please enter a valid double value with up to 2 decimal places.");
            }
        } while (!inputAmount.matches("^-?\\d+(\\.|,)?(\\d{1,2})?$"));
        inputs.put("amount", inputAmount.replace(",", "."));

        System.out.println("Please enter the start date (YYYY-MM-DD): ");
        String inputStartDate;
        LocalDate startDate = null;
        do {
            inputStartDate = terminalUtility.readLine();
            try {
                startDate = LocalDate.parse(inputStartDate);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
            }
        } while (startDate == null);
        inputs.put("start_date", startDate);

        System.out.println("Please enter the end date (YYYY-MM-DD) or leave blank if it has no end date: ");
        String inputEndDate = terminalUtility.readLine();
        LocalDate endDate = null;
        if (!inputEndDate.isBlank()) {
            try {
                endDate = LocalDate.parse(inputEndDate);
            } catch (Exception e) {
                System.out.println("Invalid date format. End date will be set to null.");
            }
        }
        inputs.put("end_date", endDate);

        System.out.println("Please enter the interval in days: ");
        int intervalInDays = -1;
        do {
            String inputInterval = terminalUtility.readLine();
            try {
                intervalInDays = Integer.parseInt(inputInterval);
                if (intervalInDays <= 0 || intervalInDays > 31) {
                    System.out.println("Interval in days must be greater than 0 and less than or equal to 31. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 31.");
            }
        } while (intervalInDays <= 0 || intervalInDays > 31);
        inputs.put("interval_in_days", intervalInDays);

        inputs.put("active", true); // Default to active

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
        System.out.println("Please enter the categories for this reaccuring transaction (type 'done' to finish):");
        System.out.println("");

        while (true) {
            String category = "";
            do {
                category = terminalUtility.readLine();
                if (category == null || category.isEmpty()) {
                    System.out.println("Category cannot be empty. Please try again.");
                }
            } while (category == null || category.isEmpty());
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