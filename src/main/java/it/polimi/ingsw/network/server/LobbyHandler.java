package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

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

    public void newGame(GameHandler newGame){
        games.add(newGame);
    }

    public String printLobby(){
        String lobby;

        lobby = "\n [LOBBY] \n";

        for (GameHandler game : games) {
            lobby += "\nGame Id : " +
                    game.getGameId() + "    " +
                    "Number of players:  " +
                    game.getNumPlayers() + " / " +
                    game.getMaxPlayers() + "    ";

            if(game.getGame().isExpertMode()) lobby += "ExpertMode : ON";
            else lobby += "ExpertMode : OFF";

            if(game.isStarted()) lobby += " already Started \n";
            else lobby += "\n";
        }

        return lobby;
    }

    public void receivedMessage(Message receivedMessage, int gameId){
        GameHandler game = games
                .stream()
                .filter(g -> g.getGameId() == gameId)
                .collect(Collectors.toList())
                .get(0);

        game.receivedMessage(receivedMessage);
    }

    public int getGameIdFromUsername(String username){
        return gameIdUsernameMap.get(username);
    }
    public List<GameHandler> getGames() {
        return games;
    }

    public void login(String username){
        usernameQueue.add(username);
    }

    public void joinGame(String username, int gameId){
        gameIdUsernameMap.put(username, gameId);
    }

    public List<String> getUsernameQueue() {
        return usernameQueue;
    }
}
