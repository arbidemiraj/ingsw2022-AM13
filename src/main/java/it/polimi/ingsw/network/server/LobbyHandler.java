package it.polimi.ingsw.network.server;

import java.util.ArrayList;
import java.util.List;

public class LobbyHandler {
    private List<GameHandler> games;
    public LobbyHandler() {
        this.games = new ArrayList<>();
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
}
