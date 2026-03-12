package com.feliperibas.countryminigame;


import com.feliperibas.countryminigame.controller.GameController;
import com.feliperibas.countryminigame.service.GameService;
import com.feliperibas.countryminigame.view.ConsoleView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        GameService service = new GameService();
        ConsoleView view = new ConsoleView();

        GameController controller = new GameController(service, view);

        controller.startGameConsole();

    }
}