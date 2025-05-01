package de.dhbw.finanztracker.ui;

import java.util.Scanner;

public class TerminalUtility {
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static int readNextInt(){
        try (Scanner scanner = new Scanner(System.in)) {
            int readInt = scanner.nextInt();
            scanner.nextLine();
            return readInt;
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return readNextInt(); 
        }
    }

    public static String readLine(){
        try (Scanner scanner = new Scanner(System.in)) {
            String readLine = scanner.nextLine();
            return readLine;
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid String.");
            return readLine(); 
        }
    }

    public static void pauseForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("An error occurred while pausing: " + e.getMessage());
        }
    }
}
