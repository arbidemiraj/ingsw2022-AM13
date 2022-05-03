package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;
import it.polimi.ingsw.network.message.servermsg.StartGame;

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
        numPlayers = 0;
        this.gameId = gameId;
    }

    public void startGame(){
        started = true;

        StartGame startGame = new StartGame();

        sendMessageToAll(startGame);

        controller.firstPlayer();

    }

    public boolean isStarted(){
        return started;
    }

    public void addPlayer(String username){
        game.addPlayer(username);
        this.numPlayers++;
        if(numPlayers == maxPlayers) startGame();
    }

    public void removePlayer(String username){
        game.getPlayers().remove(game.getPlayerByUsername(username));
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

    public void receivedMessage(Message message){
    }

    public void sendMessage(Message message, String username){
        server.getClientHandlerMap().get(username).sendMessage(message);
    }

    public void sendMessageToAll(Message message){
        for(String username : controller.getGame().getUsernames()){
            sendMessage(message, username);
        }
    }
}



