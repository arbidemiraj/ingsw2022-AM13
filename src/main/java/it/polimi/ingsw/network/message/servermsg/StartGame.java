package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class StartGame extends Message {

    public StartGame() {
        super("Server", MessageType.START_GAME);
    }

    @Override
    public String toString() {
        return "The game has started! ";
    }
}
