package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformUserdata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.ui.userInteractions.NewUserRegistration;
import de.dhbw.finanztracker.ui.userInteractions.UserSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StartController {


    public void Start(Map<String, IRepository> repositories) {
        TerminalUtility terminalUtility = new TerminalUtility();
        
        System.out.println("Starting the application...");

        IRepository r = repositories.get("userRepository");
        List<Map<String, Object>> result = r.getAll();
        User user = null;
        List<User> users = new ArrayList<>();
        Boolean shouldrun = true;
        if (result.isEmpty()) {
            user = NewUserRegistration.registerNewUser(r, terminalUtility);
        } else {
            users = TransformUserdata.TransformUsers(result);
        }

        do {
            terminalUtility.clearScreen();
            
            if (users.isEmpty()) {
                user = NewUserRegistration.registerNewUser(r, terminalUtility);
            } else {
                user = UserSelection.selectUser(users,r, terminalUtility);  
            }
            
            if (user != null) {
                AccountsOverviewController.Start(repositories,users,  user, terminalUtility);
            } else {
                shouldrun = false;   
            }

        }while(shouldrun == true);
        
    }
    
}
