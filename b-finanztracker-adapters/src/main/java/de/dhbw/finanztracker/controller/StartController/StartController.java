package de.dhbw.finanztracker.controller.StartController;

import de.dhbw.finanztracker.TransformData.TransformUserdata;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.userInteractions.NewUserRegistration;
import de.dhbw.finanztracker.ui.userInteractions.UserSelection;

import java.util.List;
import java.util.Map;

public class StartController {


    public void Start(List<IRepository> repositories) {
        System.out.println("Starting the application...");

        IRepository r = repositories.get(1);
        List<Map<String, Object>> result = r.getAll();

        if (result.isEmpty()) {
            NewUserRegistration.registerNewUser(r);
        } else {
            List<User> users = TransformUserdata.TransformUsers(result);
            UserSelection.selectUser(users,r);
        }
             
    }


    
}
