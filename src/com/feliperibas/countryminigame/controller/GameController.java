package com.feliperibas.countryminigame.controller;

import com.feliperibas.countryminigame.service.GameService;
import com.feliperibas.countryminigame.view.ConsoleView;

import java.io.IOException;

public class GameController {

    private GameService service;
    private ConsoleView view;


    public GameController(GameService service, ConsoleView view){
        this.service = service;
        this.view = view;
    }

    public void startGameConsole() throws IOException, InterruptedException {
        service.loadCountriesInfoJson();
        //service.loadCountryNamesList();

        service.setDifficulty(view.askDifficulty());

        service.loadCountryList();
        service.filterCountryList();

        // Game Loop
        boolean repeat = true;
        while (repeat){
            view.askFlagQuestion(service.getRandomFilteredCountry());

            repeat = view.askRepeat();
        }
        System.out.println("Thanks for playing!");
    }

}
