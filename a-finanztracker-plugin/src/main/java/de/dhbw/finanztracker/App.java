package de.dhbw.finanztracker;


import org.flywaydb.core.Flyway;
import io.github.cdimascio.dotenv.Dotenv;

public class App {

    public void run() {
        System.out.println("Application is running...");
        // Initialize services, load UI, etc.
        clearScreen();
        System.out.println("Welcome to the FinanzTracker Application!");

        migrateDatabase();
        
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