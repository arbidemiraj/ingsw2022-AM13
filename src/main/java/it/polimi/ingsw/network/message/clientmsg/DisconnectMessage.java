package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the user disconnects
 */
public class DisconnectMessage extends Message {


    public DisconnectMessage(String username) {
        super(username, MessageType.DISCONNECTED);
    }

    @Override
    public String toString() {
        return getUsername() +
                "got disconnected" +
                '}';
    }
}
