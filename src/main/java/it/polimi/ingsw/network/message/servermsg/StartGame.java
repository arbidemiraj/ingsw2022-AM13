package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class StartGame extends Message {

    private String firstPlayer;

    public StartGame(String firstPlayer) {
        super("Server", MessageType.START_GAME);

        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public String toString() {
        return "StartGame{" +
                "firstPlayer='" + firstPlayer + '\'' +
                '}';
    }
}
