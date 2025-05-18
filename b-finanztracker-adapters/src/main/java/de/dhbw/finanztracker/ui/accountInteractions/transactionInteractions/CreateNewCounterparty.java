package de.dhbw.finanztracker.ui.accountInteractions.transactionInteractions;

import de.dhbw.finanztracker.transaction_use_cases.CounterpartyManagement;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.ICounterparty;
import de.dhbw.finanztracker.ui.TerminalUtility;

import java.util.HashMap;
import java.util.Map;

public class CreateNewCounterparty {

    public static ICounterparty createCounterparty(IRepository repository, TerminalUtility terminalUtility) {
        terminalUtility.clearScreen();
        ICounterparty counterparty = null;

        System.out.println("Creating a new counterparty...");

        Map<String, String> inputs = getBasicInputs(terminalUtility);
        counterparty = CounterpartyManagement.createCounterparty(inputs, repository);

        if (counterparty != null) {
            System.out.println("Counterparty " + counterparty.getCounterpartyName() + " has been created successfully.");
        } else {
            System.out.println("No counterparty was created.");
        }

        terminalUtility.pauseForOneSecond();

        return counterparty;
    }

    public static Map<String, String> getBasicInputs(TerminalUtility terminalUtility) {
        Map<String, String> inputs = new HashMap<>();

        System.out.println("Please enter the counterparty name: ");
        String inputName = "";
        do {
            inputName = terminalUtility.readLine();
            if (inputName == null || inputName.isEmpty()) {
                System.out.println("Counterparty name cannot be empty. Please try again.");
                terminalUtility.pauseForOneSecond();
            }
        } while (inputName == null || inputName.isEmpty());
        inputs.put("name", inputName);

        System.out.println("Please enter the counterparty description: ");
        String inputDescription = "";
        do {
            inputDescription = terminalUtility.readLine();
            if (inputDescription == null || inputDescription.isEmpty()) {
                System.out.println("Counterparty description cannot be empty. Please try again.");
                terminalUtility.pauseForOneSecond();
            }
        } while (inputDescription == null || inputDescription.isEmpty());
        inputs.put("description", inputDescription);

        return inputs;
    }
}