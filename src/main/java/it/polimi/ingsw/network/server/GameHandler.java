package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.GenericType;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.ChooseCloudMessage;
import it.polimi.ingsw.network.message.clientmsg.ChooseTowerColorMessage;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;
import it.polimi.ingsw.network.message.servermsg.AskTowerColor;
import it.polimi.ingsw.network.message.servermsg.StartGame;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class that handles a single game
 */
public class GameHandler {
    private final GameController gameController;
    private final Server server;
    private ArrayList<TowerColor> towerColors;
    private List<Integer> availableWizards;
    private final Game game;
    private int numPlayers;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private int gameId;
    private final int maxPlayers;
    private boolean started;
    private boolean isActive = true;


    /**
     * Default constructor
     * @param server class
     * @param newGameMessage message that contains the game settings
     * @param gameId number of the match
     */
    public GameHandler(Server server, NewGameMessage newGameMessage, int gameId){
        this.server = server;
        started = false;
        maxPlayers = newGameMessage.getMaxPlayers();
        game = new Game(maxPlayers, newGameMessage.isExpertMode());
        gameController = new GameController(game, this);

        game.addObserver(gameController);

        numPlayers = 0;
        this.gameId = gameId;

        towerColors = new ArrayList<>();
        towerColors.add(TowerColor.BLACK);
        towerColors.add(TowerColor.GRAY);
        towerColors.add(TowerColor.WHITE);

        availableWizards = new ArrayList<>();

        for(int i = 1; i <= 4; i++){
            availableWizards.add(i);
        }
    }

    /**
     * returns true if the game is active
     * @return true if the game is active
     */
    public boolean isActive() {
        return isActive;
    }

    public void startGame(){
        boolean color = true;

        while(color){
            for(Player player : game.getPlayers()){
                if(player.getTowerColor() != null) color = false;
            }
        }

        started = true;
        game.startGame();

        gameController.startGame();
    }

    public boolean isStarted(){
        return started;
    }

    /**
     * Adds a player to the game
     * @param username the username of the player to add
     */
    public void addPlayer(String username){
        gameController.addPlayer(username);
        askTowerColor(username);
    }

    /**
     * removes a player from the game
     * @param username the username of the player to remove
     */
    public void removePlayer(String username){
        game.getPlayers().remove(game.getPlayerByUsername(username));
    }

    /**
     * Handles the end of a game
     */
    public void endGame(){
        for(String username : gameController.getGame().getUsernames()){
            server.removeClient(username);
            gameController.getGame().removePlayer(username);
        }

        isActive = false;
        started = false;
    }

    /**
     * Handles the end  of a game due to a disconnection
     * @param username the username of the player that disconnected
     */
    public void endGame(String username){
        for(String user : gameController.getGame().getUsernames()){
            server.removeClient(user);
            gameController.getGame().removePlayer(user);
        }

        isActive = false;
        started = false;
    }

    /**
     * @param username to who is asked the tower colour
     */
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

    /**
     * Handles the received message
     * @param message sent from the client that chose the tower colour
     */
    public void receivedMessage(Message message){
        switch (message.getMessageType()){
            case TOWER_COLOR_CHOOSE -> {
                ChooseTowerColorMessage towerColorMessage = (ChooseTowerColorMessage) message;
                towerColors.remove(towerColorMessage.getChosenTowerColor());

                game.getPlayerByUsername(message.getUsername())
                        .setTowerColor((towerColorMessage.getChosenTowerColor()));

                this.numPlayers++;

                if(numPlayers == maxPlayers) startGame();
            }

            default -> {
                gameController.messageReceived(message);
            }
        }
    }

    /**
     * Sends a message to a single user
     * @param message the message sent to the client
     * @param username of the player that receives the message
     */
    public void sendMessage(Message message, String username){
        server.getClientHandlerMap().get(username).sendMessage(message);
    }

    /**
     * Sends a message to all the users
     * @param message asks the username to everyone
     */
    public void sendMessageToAll(Message message){
        for(String username : gameController.getGame().getUsernames()){
            sendMessage(message, username);
        }
    }

    /**
     * Sends the message to all the users except the given one
     * @param message the message to send to the clients
     * @param user the username of the user excluded
     */
    public void sendMessageToAllExcept(Message message, String user){
        for(String username : gameController.getGame().getUsernames()){
            if(!username.equals(user)) sendMessage(message, username);
        }
    }


}



