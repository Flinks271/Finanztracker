package de.dhbw.finanztracker.ui;

import java.util.List;
import java.util.Scanner;

public class TerminalUtility {

    public static final Scanner scanner = new Scanner(System.in);

    public TerminalUtility() {
        
    }

    public void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public int readNextInt(){
       int readInt = -1; 
        try  {
            readInt = scanner.nextInt();
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
        return readInt;
    }

    public String readLine(){
        String readLine = null;
        try {
            readLine = scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid String.");
        }
        return readLine;
    }

    public void pauseForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("An error occurred while pausing: " + e.getMessage());
        }
    }

    public String generateHyphenLineOneSide(List<Integer> distance){
        StringBuilder hyphenLine = new StringBuilder();
        for (int i = 0; i < distance.size(); i++) {
            hyphenLine.append("-".repeat(distance.get(i)));
            if (i != distance.size() - 1) {
                hyphenLine.append("+");
            }
        }
        return hyphenLine.toString();
    }

    public String generateHyphenLineOneSideTop(List<Integer> distance){
        StringBuilder hyphenLine = new StringBuilder();
        for (int i = 0; i < distance.size(); i++) {
            hyphenLine.append("-".repeat(distance.get(i)));
            if (i != distance.size() - 1) {
                hyphenLine.append("┴");
            }
        }
        return hyphenLine.toString();
    }

    public String generateHyphenLineTwoSides(List<Integer> distanceTop, List<Integer> distanceBottom){
        StringBuilder hyphenLine = new StringBuilder();
        int maxSize = Math.max(distanceTop.size(), distanceBottom.size());
        int nextTop = distanceTop.getFirst();
        int nextBottom = distanceBottom.getFirst();
        int indexTop = 1;
        int indexBottom = 1;
        Boolean loadTop = false;
        Boolean loadBottom = false;
        for (int i = 0; i < maxSize; i++) {

            if (nextTop == 0 && nextBottom == 0) {
                hyphenLine.append("+");
                loadTop = true;
                loadBottom = true;
            } else if (nextTop == 0) {
                hyphenLine.append("┴");
                loadTop = true;
            } else if (nextBottom == 0) {
                hyphenLine.append("┬");
                loadBottom = true;
            }             else {
                hyphenLine.append("-");
                nextBottom--;
                nextTop--;
            }


            if(loadBottom && distanceBottom.size() > indexBottom) {
                nextBottom = distanceBottom.get(indexBottom);
                indexBottom++;
                loadBottom = false;
            } 
            if(loadTop && distanceTop.size() > indexTop) {
                nextTop = distanceTop.get(indexTop);
                indexTop++;
                loadTop = false;
            }     
        }
        return hyphenLine.toString();
    }
}
