package com.feliperibas.countryminigame;


import com.feliperibas.countryminigame.controller.GameController;
import com.feliperibas.countryminigame.model.Country;
import com.feliperibas.countryminigame.service.GameService;
import com.feliperibas.countryminigame.view.ConsoleView;
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

        GameService service = new GameService();
        ConsoleView view = new ConsoleView();

        GameController controller = new GameController(service, view);

        controller.startGameConsole();

        /*


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


        }while (again);

         */
    }
}