package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.exceptions.InvalidGameIdException;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.GenericType;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.servermsg.NotifyDisconnectionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that handles the lobby for multiple games feature
 */
public class LobbyHandler {
    private List<GameHandler> games;
    private List<String> usernameQueue;
    private Map<String, Integer> gameIdUsernameMap;

    /**
     * Default constructor
     */
    public LobbyHandler() {
        this.games = new ArrayList<>();
        this.usernameQueue = new ArrayList<>();
        gameIdUsernameMap = new HashMap<>();
    }

    /**
     * Creates a new game and adds it to games list
     * @param newGame the class that handles the game
     */
    public void newGame(GameHandler newGame){
        games.add(newGame);
    }

    /**
     * Prints the lobby containing all the existing games
     * @return lobby information
     */
    public String printLobby(){
        String lobby;

        lobby = "\n [LOBBY] \n";

        for (GameHandler game : games) {
            if(game.isActive() && !game.isStarted() && game.getNumPlayers() != 0) {
                lobby += "\nGame Id : " +
                        game.getGameId() + "    " +
                        "Number of players:  " +
                        game.getNumPlayers() + " / " +
                        game.getMaxPlayers() + "    ";

                if (game.getGame().isExpertMode()) lobby += "ExpertMode : ON";
                else lobby += "ExpertMode : OFF";

                if (game.isStarted()) lobby += " already Started \n";
                else lobby += "\n";
            }
        }

        return lobby;
    }

    /**
     * Handles a received message from the client and sends it to the associated game handler
     * @param receivedMessage the received message
     * @param gameId the id of the game of the message
     */
    public void receivedMessage(Message receivedMessage, int gameId){
        GameHandler game = games
                .stream()
                .filter(g -> g.getGameId() == gameId)
                .collect(Collectors.toList())
                .get(0);

        game.receivedMessage(receivedMessage);
    }

    /**
     * Returns the game id the player is playing
     * @param username the username of the player whose game id you need
     * @return the game id or error if there is no username
     */
    public int getGameIdFromUsername(String username){
        if(gameIdUsernameMap.get(username) != null){
            return gameIdUsernameMap.get(username);
        }else{
            return -1;
        }

    }

    public List<GameHandler> getGames() {
        return games;
    }

    /**
     * Adds a username to the username queue list
     * @param username to add to the list of players
     */
    public void login(String username){
        usernameQueue.add(username);
    }

    /**
     * Adds to the game id username map the username and the game id, to associate a game id to the player
     * @param username that wants to join the game
     * @param gameId to join
     */
    public void joinGame(String username, int gameId) throws InvalidGameIdException {
        try{
            games.get(gameId);
        }catch (IndexOutOfBoundsException e){
            throw  new InvalidGameIdException();
        }

        if(games.get(gameId) != null && games.get(gameId).isStarted());
        else gameIdUsernameMap.put(username, gameId);

    }

    public List<String> getUsernameQueue() {
        return usernameQueue;
    }

    /**
     * Handles the disconnection of a user
     * @param username that drops the connection
     */
    public void disconnect(String username) {
        if(getGameIdFromUsername(username) != -1){
            games.get(getGameIdFromUsername(username)).sendMessageToAllExcept(new NotifyDisconnectionMessage(username), username);

            for(String user : games.get(getGameIdFromUsername(username)).getGame().getUsernames()){
                usernameQueue.remove(user);
            }

            games.get(getGameIdFromUsername(username)).endGame(username);

            gameIdUsernameMap.remove(username);
        }
    }
}
