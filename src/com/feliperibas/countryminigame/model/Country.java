package com.feliperibas.countryminigame.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.Arrays;

public class Country {
    private String name;
    private String cca3;
    private boolean independent;
    private String[] capital;
    private String[] continents;
    private int population;
    private String flag;

    public Country() {
    }

    public Country(String json){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

        this.name = jsonArray.get(0).getAsJsonObject().get("name")
                .getAsJsonObject().get("common").getAsString();
        this.cca3 = jsonArray.get(0).getAsJsonObject().get("cca3").getAsString();
        this.independent = jsonArray.get(0).getAsJsonObject().get("independent").getAsBoolean();

        // Capital
        try {
            this.capital = new String[jsonArray.get(0).getAsJsonObject().get("capital").getAsJsonArray().size()];
            for (int i = 0; i < capital.length; i++) {
                this.capital[i] = jsonArray.get(0).getAsJsonObject().get("capital").getAsJsonArray().get(i).getAsString();
            }
        } catch (Exception E){
            this.capital = new String[1];
            this.capital[0] = "null";
        }

        // Continents
        this.continents = new String[jsonArray.get(0).getAsJsonObject().get("continents").getAsJsonArray().size()];
        for (int i = 0; i < continents.length; i ++) {
            this.continents[i] = jsonArray.get(0).getAsJsonObject().get("continents").getAsJsonArray().get(i).getAsString();
        }

        this.population = jsonArray.get(0).getAsJsonObject().get("population").getAsInt();
        this.flag = jsonArray.get(0).getAsJsonObject().get("flags").getAsJsonObject().get("png").getAsString();
    }


    public Country(String name, String cca3, boolean independent, String[] capital, String[] continents, int population, String flag) {
        this.name = name;
        this.cca3 = cca3;
        this.independent = independent;
        this.capital = capital;
        this.continents = continents;
        this.population = population;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", cca3='" + cca3 + '\'' +
                ", independent='" + independent + '\'' +
                ", capital=" + Arrays.toString(capital) +
                ", continents=" + Arrays.toString(continents) +
                ", population=" + population +
                ", flag='" + flag + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public boolean getIndependent() {
        return independent;
    }

    public void setIndependent(boolean independent) {
        this.independent = independent;
    }

    public String[] getCapital() {
        return capital;
    }

    public void setCapital(String[] capital) {
        this.capital = capital;
    }

    public String[] getContinents() {
        return continents;
    }

    public void setContinents(String[] continents) {
        this.continents = continents;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
