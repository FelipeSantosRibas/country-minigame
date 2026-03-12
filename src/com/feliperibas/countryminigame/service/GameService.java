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
import java.util.Random;

public class GameService {

    private String countriesInfoJson;
    // private List<String> countryNamesList;
    private List<Country> filteredCountryList;
    private List<Country> countryList;
    private Country selectedCountry;
    private String difficulty;

    // Getters and Setters

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
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

    /*
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
     */

    public List loadCountryList(){

        // Instantiate Gson
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(countriesInfoJson, JsonArray.class);


        if (countryList != null){
            countryList.clear();
        } else{
            countryList = new ArrayList<>();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            countryList.add(i, new Country());
            countryList.get(i).setName(jsonArray.get(i).getAsJsonObject().get("name")
                    .getAsJsonObject().get("common").getAsString());
            countryList.get(i).setCca3(jsonArray.get(i).getAsJsonObject().get("cca3").getAsString());
            countryList.get(i).setIndependent(jsonArray.get(i).getAsJsonObject().get("independent").getAsBoolean());

            // Capital
            try {
                countryList.get(i).setCapital( new String[jsonArray.get(i).getAsJsonObject().get("capital").getAsJsonArray().size()]);
                for (int i2 = 0; i2 < countryList.get(i).getCapital().length; i2++) {
                    countryList.get(i).getCapital()[i2] = jsonArray.get(i).getAsJsonObject().get("capital").getAsJsonArray().get(i2).getAsString();
                }
            } catch (Exception E) {
                countryList.get(i).setCapital( new String[1]);
                countryList.get(i).getCapital()[0] = "null";
            }

            // Continents
            countryList.get(i).setContinents(new String[jsonArray.get(i).getAsJsonObject().get("continents").getAsJsonArray().size()]);
            for (int i2 = 0; i2 < countryList.get(i).getContinents().length; i2++) {
                countryList.get(i).getContinents()[i2] = jsonArray.get(i).getAsJsonObject().get("continents").getAsJsonArray().get(i2).getAsString();
            }

            countryList.get(i).setPopulation(jsonArray.get(i).getAsJsonObject().get("population").getAsInt());
            countryList.get(i).setFlag(jsonArray.get(i).getAsJsonObject().get("flags").getAsJsonObject().get("png").getAsString());
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
        int finalMinimumPopulation = minimumPopulation;
        boolean finalNeedsToBeIndependent = needsToBeIndependent;
        filteredCountryList = countryList.stream().filter(i -> i.getPopulation() >= finalMinimumPopulation).toList();
        if (finalNeedsToBeIndependent){
            filteredCountryList = filteredCountryList.stream().filter(i -> i.getIndependent()).toList();
        }
        return filteredCountryList;
    }

    public Country getRandomFilteredCountry() {
        Random random = new Random();
        return selectedCountry = filteredCountryList.get(random.nextInt(filteredCountryList.size()-1));
    }
}
