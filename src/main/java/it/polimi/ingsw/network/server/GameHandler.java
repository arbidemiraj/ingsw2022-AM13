package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;

import java.util.logging.Logger;

public class GameHandler {
    private final Controller controller;
    private final Server server;
    private final Game game;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private int gameId;

    public GameHandler(Server server, NewGameMessage newGameMessage, int gameId){
        this.server = server;
        game = new Game(newGameMessage.getMaxPlayers(), newGameMessage.isExpertMode());
        controller = new Controller(game);
        this.gameId = gameId;
    }

    public void startGame(){

    }

    public void addPlayer(){

    }

    public void removePlayer(){

    }

    public void endGame(){

    }
    public void gameSetup(){

    }
}



