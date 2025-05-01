package de.dhbw.finanztracker.controller;

import de.dhbw.finanztracker.TransformData.TransformUserdata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.TerminalUtility;
import de.dhbw.finanztracker.ui.userInteractions.NewUserRegistration;
import de.dhbw.finanztracker.ui.userInteractions.UserSelection;

import java.util.List;
import java.util.Map;

public class StartController {


    public void Start(List<IRepository> repositories) {
        TerminalUtility terminalUtility = new TerminalUtility();
        
        System.out.println("Starting the application...");

        IRepository r = repositories.get(1);
        List<Map<String, Object>> result = r.getAll();
        User user = null;
        Boolean shouldrun = true;

        do {
            if (result.isEmpty()) {
                user = NewUserRegistration.registerNewUser(r, terminalUtility);
            } else {
                List<User> users = TransformUserdata.TransformUsers(result);
                user = UserSelection.selectUser(users,r, terminalUtility);
            }
            
            if (user != null) {
                AccountsOverviewController.Start(repositories, user, terminalUtility);
            } else {
                shouldrun = false;   
            }

        }while(shouldrun == true);
        
    }
    
}
