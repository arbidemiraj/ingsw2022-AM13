package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class DisconnectionMessage extends Message {


    public DisconnectionMessage(String username) {
        super(username, MessageType.DISCONNECTED);
    }

    @Override
    public String toString() {
        return getUsername() +
                "got disconnected" +
                '}';
    }
}
