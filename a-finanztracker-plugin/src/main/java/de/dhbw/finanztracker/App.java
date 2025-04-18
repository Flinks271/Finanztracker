package de.dhbw.finanztracker;

public class App {

    public void run() {
        System.out.println("Application is running...");
        // Initialize services, load UI, etc.
        clearScreen();
        System.out.println("Welcome to the FinanzTracker Application!");
        
    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}