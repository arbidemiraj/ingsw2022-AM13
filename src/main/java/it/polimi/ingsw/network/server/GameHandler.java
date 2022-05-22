package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.ChooseCloudMessage;
import it.polimi.ingsw.network.message.clientmsg.ChooseTowerColorMessage;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;
import it.polimi.ingsw.network.message.servermsg.AskTowerColor;
import it.polimi.ingsw.network.message.servermsg.StartGame;

import java.util.ArrayList;
import java.util.logging.Logger;

public class GameHandler {
    private final GameController gameController;
    private final Server server;
    private ArrayList<TowerColor> towerColors;
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
        gameController = new GameController(game, this);
        numPlayers = 0;
        this.gameId = gameId;

        towerColors = new ArrayList<>();
        towerColors.add(TowerColor.BLACK);
        towerColors.add(TowerColor.GRAY);
        towerColors.add(TowerColor.WHITE);
    }

    public void startGame(){
        started = true;

        game.getBoard().prepareGame();
        game.startGame();

        gameSetup();

        gameController.startGame();
    }

    public boolean isStarted(){
        return started;
    }

    public void addPlayer(String username){
        gameController.addPlayer(username);
        askTowerColor(username);

        this.numPlayers++;
        if(numPlayers == maxPlayers) startGame();
    }

    public void removePlayer(String username){
        game.getPlayers().remove(game.getPlayerByUsername(username));
    }

    public void endGame(){

    }

    public void gameSetup(){
        gameController.firstPlayer();

        sendMessage(new GenericMessage("\n You are the first player! "), gameController.getCurrentPlayerUsername());
        sendMessageToAllExcept(new GenericMessage("\n Wait... " + gameController.getCurrentPlayerUsername() + " is playing his turn! "), gameController.getCurrentPlayerUsername());
    }

    private void askTowerColor(String username) {
        sendMessage(new AskTowerColor(towerColors), username);
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
        switch (message.getMessageType()){
            case TOWER_COLOR_CHOOSE -> {
                ChooseTowerColorMessage towerColorMessage = (ChooseTowerColorMessage) message;
                towerColors.remove(towerColorMessage.getChosenTowerColor());

                game.getPlayerByUsername(message.getUsername())
                        .setTowerColor((towerColorMessage.getChosenTowerColor()));
            }

            default -> {
                gameController.messageReceived(message);
            }
        }
    }

    public void sendMessage(Message message, String username){
        server.getClientHandlerMap().get(username).sendMessage(message);
    }

    public void sendMessageToAll(Message message){
        for(String username : gameController.getGame().getUsernames()){
            sendMessage(message, username);
        }
    }

    public void sendMessageToAllExcept(Message message, String user){
        for(String username : gameController.getGame().getUsernames()){
            if(username != user) sendMessage(message, username);
        }
    }


}



