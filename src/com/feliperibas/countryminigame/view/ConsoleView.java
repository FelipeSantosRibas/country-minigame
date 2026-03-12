package com.feliperibas.countryminigame.view;

import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner = new Scanner(System.in);

    public String askDifficulty(){
        System.out.println("Select the Difficulty:");
        System.out.println("Easy (Only independent 10m+ population countries)");
        System.out.println("Medium (Only 500k+ population countries)");
        System.out.println("Hard (Every nation)");

        String difficulty = "";
        boolean needsToBeIndependent = true;
        int minimumPopulation = 0;

        Scanner scanner = new Scanner(System.in);
        String in = "";
        for (boolean repeat = true; repeat;){
            in = scanner.nextLine();

            // Check valid answer
            if (in.equalsIgnoreCase("hard")){
                difficulty = "hard";
                repeat = false;
            } else if(in.equalsIgnoreCase("medium")){
                difficulty = "medium";
                repeat = false;
            } else if(in.equalsIgnoreCase("easy")){
                difficulty = "easy";
                repeat = false;
            } else{
                System.out.println("Please, write a valid difficulty");
                repeat = true;
            }
        }
        // Return difficulty with first letter in uppercase
        System.out.println("You chose difficulty: "+difficulty
                .replaceFirst(difficulty.substring(0,1),difficulty.substring(0,1).toUpperCase()));

        return difficulty;
    }

    public boolean askRepeat(){
        System.out.println("Wanna try again?");
        String in;
        do {
            in = scanner.nextLine();
            if (!in.equalsIgnoreCase("yes")&&!in.equalsIgnoreCase("no")){
                System.out.println("Please, answer with yes or no.");
            }
        } while (!in.equalsIgnoreCase("yes")&&!in.equalsIgnoreCase("no"));

        return in.equalsIgnoreCase("yes");
    }
}
