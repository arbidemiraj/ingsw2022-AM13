package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameHandler {
    private final Controller controller;
    private final Server server;
    private final Game game;
    private int numPlayers;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private int gameId;
    private final int maxPlayers;
    private boolean started;

    public GameHandler(Server server, NewGameMessage newGameMessage, int gameId){
        this.server = server;
        started = false;
        maxPlayers = newGameMessage.getMaxPlayers();
        game = new Game(maxPlayers, newGameMessage.isExpertMode());
        controller = new Controller(game);
        numPlayers = 1;
        this.gameId = gameId;
    }

    public void startGame(){
        started = true;
    }

    public boolean isStarted(){
        return started;
    }

    public void addPlayer(){
        this.numPlayers++;
    }

    public void removePlayer(){

    }

    public void endGame(){

    }
    public void gameSetup(){

    }

    public int getGameId() {
        return gameId;
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Game getGame() {
        return game;
    }
}



