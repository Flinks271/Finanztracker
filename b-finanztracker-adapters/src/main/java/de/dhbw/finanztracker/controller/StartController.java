package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformUserdata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.userInteractions.NewUserRegistration;
import de.dhbw.finanztracker.ui.userInteractions.UserSelection;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StartController {


    public void Start(List<IRepository> repositories) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Starting the application...");

        IRepository r = repositories.get(1);
        List<Map<String, Object>> result = r.getAll();
        User user = null;

        if (result.isEmpty()) {
            NewUserRegistration.registerNewUser(r,scanner);
        } else {
            List<User> users = TransformUserdata.TransformUsers(result);
            user = UserSelection.selectUser(users,r,scanner);
        }
        
        AccountsOverviewController.Start(repositories, user, scanner);
    }
    
}
