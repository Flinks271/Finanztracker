package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.accountInteractions.CreateNewAccount;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccountsOverviewController {

    public static void Start(List<IRepository> repositories, User user, Scanner scanner) {
        System.out.println("Starting the AccountOverview...");
        System.out.println("Loading accounts for user: " + user.getUsername());
        IRepository accountRepository = repositories.get(0);
        List<Map<String, Object>> result = accountRepository.getWhere("user_id = '" + user.getUserId() + "'");

        if (result.isEmpty()) {
            CreateNewAccount.createAccount(accountRepository, user,scanner);
        } else {
            
            
        }
        



    }
    
}
