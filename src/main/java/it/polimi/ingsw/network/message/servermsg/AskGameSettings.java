package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskGameSettings extends Message {

    public AskGameSettings() {
        super("Server", MessageType.ASK_GAME_SETTINGS);
    }

    @Override
    public String toString() {
        return "AskGameSettings{}";
    }
}
