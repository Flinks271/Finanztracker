package de.dhbw.finanztracker.ui;

import java.util.Scanner;

public class TerminalUtility {

    public static final Scanner scanner = new Scanner(System.in);

    public TerminalUtility() {
        
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static int readNextInt(){
       int readInt = -1; 
        try  {
            readInt = scanner.nextInt();
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return readNextInt(); 
        }
        return readInt;
    }

    public static String readLine(){
        String readLine = null;
        try {
            readLine = scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid String.");
            //return readLine(); 
        }
        return readLine;
    }

    public static void pauseForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("An error occurred while pausing: " + e.getMessage());
        }
    }
}
