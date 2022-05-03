package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * The player wants to reload a game that recently went down
 */

public class ReloadGameMessage extends Message {


    public ReloadGameMessage(String username) {
        super(username, MessageType.RELOAD_GAME);
    }


    @Override
    public String toString() {
        return "ReloadGameMessage{" +
                "username=" + getUsername() +
                '}';
    }

}