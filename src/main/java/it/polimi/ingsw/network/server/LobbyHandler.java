package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.GenericType;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.servermsg.NotifyDisconnectionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LobbyHandler {
    private List<GameHandler> games;
    private List<String> usernameQueue;
    private Map<String, Integer> gameIdUsernameMap;

    public LobbyHandler() {
        this.games = new ArrayList<>();
        this.usernameQueue = new ArrayList<>();
        gameIdUsernameMap = new HashMap<>();
    }

    /**
     *
     * @param newGame that the handler has to track
     */
    public void newGame(GameHandler newGame){
        games.add(newGame);
    }

    /**
     *
     * @return lobby information
     */
    public String printLobby(){
        String lobby;

        lobby = "\n [LOBBY] \n";

        for (GameHandler game : games) {
            if(game.isActive() && !game.isStarted()) {
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
     *
     * @param receivedMessage
     * @param gameId
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
     *
     * @param username
     * @return error if there is no username
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
     *
     * @param username to add to the list of players
     */
    public void login(String username){
        usernameQueue.add(username);
    }

    /**
     *
     * @param username that wants to join the game
     * @param gameId to join
     */
    public void joinGame(String username, int gameId){
        gameIdUsernameMap.put(username, gameId);
    }

    public List<String> getUsernameQueue() {
        return usernameQueue;
    }

    /**
     *
     * @param username that drops the connection
     */
    public void disconnect(String username) {
        if(getGameIdFromUsername(username) != -1){
            games.get(getGameIdFromUsername(username)).sendMessageToAllExcept(new NotifyDisconnectionMessage(username), username);

            games.get(getGameIdFromUsername(username)).endGame(username);

            gameIdUsernameMap.remove(username);

            usernameQueue.remove(username);
        }
    }
}
