package com.feliperibas.countryminigame.service;

import com.feliperibas.countryminigame.model.Country;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GameService {

    private String countriesInfoJson;
    private List<String> countryNamesList;
    private List<String> filteredCountryNamesList;
    private List<Country> countryList;
    private String difficulty;

    // Getters and Setters

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    // Other methods


    public String loadCountriesInfoJson() throws IOException, InterruptedException {
        // https://restcountries.com/v3.1/all?fields=name,cca3,independent,capital,continents,population,flags
        // Protocolo HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://restcountries.com/v3.1/all?fields=name,cca3,independent,capital,continents,population,flags"))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        return this.countriesInfoJson = response.body(); // JSON info of all countries


    }

    public List loadCountryNamesList(){

        // JSON treatment to get country names list to array
        Gson gson = new Gson();
        JsonArray jsonNamesArray = gson.fromJson(countriesInfoJson, JsonArray.class);
        ArrayList<String> namesArray = new ArrayList<>();
        for (int i = 0; i < jsonNamesArray.size(); i++) {
            namesArray.add(jsonNamesArray.get(i).getAsJsonObject().get("name")
                    .getAsJsonObject().get("common").getAsString());
        }
        return this.countryNamesList = namesArray; // Array with all countries names
    }

    public List loadCountryList(){

        // Instantiate Gson
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(countriesInfoJson, JsonArray.class);


        countryList.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            countryList.set(i, new Country());
            countryList.get(i).setName(jsonArray.get(0).getAsJsonObject().get("name")
                    .getAsJsonObject().get("common").getAsString());
            countryList.get(i).setCca3(jsonArray.get(0).getAsJsonObject().get("cca3").getAsString());
            countryList.get(i).setIndependent(jsonArray.get(0).getAsJsonObject().get("independent").getAsBoolean());

            // Capital
            try {
                countryList.get(i).setCapital( new String[jsonArray.get(0).getAsJsonObject().get("capital").getAsJsonArray().size()]);
                for (int i2 = 0; i2 < countryList.get(i).getCapital().length; i2++) {
                    countryList.get(i).getCapital()[i2] = jsonArray.get(0).getAsJsonObject().get("capital").getAsJsonArray().get(i).getAsString();
                }
            } catch (Exception E) {
                countryList.get(i).setCapital( new String[1]);
                countryList.get(i).getCapital()[0] = "null";
            }

            // Continents
            this.continents = new String[jsonArray.get(0).getAsJsonObject().get("continents").getAsJsonArray().size()];
            for (int i = 0; i < continents.length; i++) {
                this.continents[i] = jsonArray.get(0).getAsJsonObject().get("continents").getAsJsonArray().get(i).getAsString();
            }

            this.population = jsonArray.get(0).getAsJsonObject().get("population").getAsInt();
            this.flag = jsonArray.get(0).getAsJsonObject().get("flags").getAsJsonObject().get("png").getAsString();
        }


        return countryList;
    }

    public List filterCountryList(){
        boolean needsToBeIndependent = false;
        int minimumPopulation = 0;

        // Defining filters
        switch (difficulty) {
            case "easy" -> {
                needsToBeIndependent = true;
                minimumPopulation = 10000000;
            }
            case "medium" -> {
                needsToBeIndependent = false;
                minimumPopulation = 500000;
            }
            case "hard" -> {
                needsToBeIndependent = false;
                minimumPopulation = 0;
            }
        };

        // Using Stream API to filter
        filteredCountryNamesList = countryNamesList;

        //filteredCountryNamesList.stream().


        return filteredCountryNamesList;
    }
}
