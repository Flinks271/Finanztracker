package de.dhbw.finanztracker.controller.StartController;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.user.User;
import de.dhbw.finanztracker.ui.userselection.UserSelection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StartController {


    public void Start(List<IRepository> repositories) {
        System.out.println("Starting the application...");

        IRepository r = repositories.get(1);
        ResultSet resultSet = r.getAll();

        try {
            if (resultSet.isClosed()) {
                UserSelection.newUserRegistration(r);
            } else {
                
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
    }


    
}
