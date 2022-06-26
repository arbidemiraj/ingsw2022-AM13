package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server when the user disconnects
 */
public class DisconnectMessage extends Message {


    @Serial
    private static final long serialVersionUID = 2007263172372196775L;

    public DisconnectMessage(String username) {
        super(username, MessageType.DISCONNECTED);
    }

    @Override
    public String toString() {
        return "DisconnectMessage{}";
    }
}
