package com.feliperibas.countryminigame;


import com.feliperibas.countryminigame.model.Country;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Protocolo HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://restcountries.com/v3.1/all?fields=name"))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());

        // JSON treatment to get country names list to array
        Gson gson = new Gson();
        JsonArray jsonNamesArray = gson.fromJson(response.body(), JsonArray.class);
        String[] namesArray = new String[jsonNamesArray.size()];
        for (int i = 0; i < jsonNamesArray.size(); i++){
            namesArray[i] = jsonNamesArray.get(i).getAsJsonObject().get("name")
                    .getAsJsonObject().get("common").getAsString();

        }
        System.out.println(Arrays.toString(namesArray));


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
            if (in.equalsIgnoreCase("hard")){
                difficulty = "hard";
                needsToBeIndependent = false;
                minimumPopulation = 0;
                repeat = false;
            } else if(in.equalsIgnoreCase("medium")){
                difficulty = "medium";
                needsToBeIndependent = false;
                minimumPopulation = 500000;
                repeat = false;
            } else if(in.equalsIgnoreCase("easy")){
                difficulty = "easy";
                needsToBeIndependent = true;
                minimumPopulation = 10000000;
                repeat = false;
            } else{
                System.out.println("Please, write a valid difficulty");
                repeat = true;
            }
        }
        System.out.println("You chose difficulty: "+difficulty);

        Country country = new Country();
        boolean again = false;
        do { // Game cycle
            boolean validCountry = false;
            do { // Choose valid country

                // Get random country from the list
                Random random = new Random();
                String countryName = namesArray[random.nextInt(namesArray.length)];
                //System.out.println(countryName);
                //System.out.println(namesArray.length);

                // Get country info
                request = HttpRequest.newBuilder()
                        .uri(URI.create("https://restcountries.com/v3.1/name/" + countryName.replaceAll(" ", "%20")))
                        .build();
                response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                //System.out.println(response.body());
                country = new Country(response.body());
                // System.out.println(country);

                if (country.getPopulation()>=minimumPopulation){
                    if(needsToBeIndependent) {
                        validCountry = country.getIndependent();
                    } else{
                        validCountry = true;
                    }
                }

            } while (!validCountry);

            //System.out.println("Population: "+ country.getPopulation());
            //System.out.println("Independent: "+ country.getIndependent());

            System.out.println("Guess the country by the flag (" + country.getFlag() + "):");
            in = scanner.nextLine();
            if (in.equalsIgnoreCase(country.getName())) {
                System.out.println("Congratulations! You guessed right, the country is " + country.getName() + "!");
            } else {
                System.out.println("No! The country was " + country.getName() + ".");
            }

            System.out.println("Wanna try again?");
            do {
                in = scanner.nextLine();
                if (!in.equalsIgnoreCase("yes")&&!in.equalsIgnoreCase("no")){
                    System.out.println("Please, answer with yes or no.");
                }
            } while (!in.equalsIgnoreCase("yes")&&!in.equalsIgnoreCase("no"));

            again = in.equalsIgnoreCase("yes");
        }while (again);
    }
}