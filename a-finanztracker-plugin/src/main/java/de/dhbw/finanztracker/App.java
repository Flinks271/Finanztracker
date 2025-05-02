package de.dhbw.finanztracker;

import de.dhbw.finanztracker.controller.StartController;
import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.persistance.jdbc.account.AccountRepository;
import de.dhbw.finanztracker.persistance.jdbc.transactions.CategoriesRepository;
import de.dhbw.finanztracker.persistance.jdbc.transactions.CounterpartyRepository;
import de.dhbw.finanztracker.persistance.jdbc.transactions.ReaccuringRepository;
import de.dhbw.finanztracker.persistance.jdbc.transactions.RelationRaccuringCategorieRepository;
import de.dhbw.finanztracker.persistance.jdbc.transactions.RelationTransactionCategorieRepsository;
import de.dhbw.finanztracker.persistance.jdbc.transactions.TransactionRepository;
import de.dhbw.finanztracker.persistance.jdbc.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

import org.flywaydb.core.Flyway;
import io.github.cdimascio.dotenv.Dotenv;

public class App {

    public void run() {
        System.out.println("Application is running...");
        // Initialize services, load UI, etc.
        clearScreen();
        System.out.println("Welcome to the FinanzTracker Application!");

        migrateDatabase();

        Map<String, IRepository> repositories = new HashMap<>();
        repositories.put("accountRepository", new AccountRepository());
        repositories.put("userRepository", new UserRepository());
        repositories.put("transactionRepository", new TransactionRepository());
        repositories.put("reaccuringRepository", new ReaccuringRepository()); 
        repositories.put("categoriesRepository", new CategoriesRepository());
        repositories.put("counterpartyRepository", new CounterpartyRepository());
        repositories.put("relationTransactionCategorieRepsository", new RelationTransactionCategorieRepsository());
        repositories.put("relationReaccuringCategorieRepsository", new RelationRaccuringCategorieRepository());
        
        StartController startController = new StartController();
        startController.Start(repositories);
        
    }

    public static void migrateDatabase() {
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("POSTGRES_URL");
        String dbUser = dotenv.get("POSTGRES_USER");
        String dbPassword = dotenv.get("POSTGRES_PASSWORD");

        System.out.println("Migrating database...");

        System.err.println("DB_URL: " + dbUrl);
        
        Flyway flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .load();
        flyway.migrate();

        System.out.println("Flyway migrations completed.");
        System.out.println("Application is running...");
    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}