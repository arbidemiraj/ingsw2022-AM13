package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class EndTurnMessage extends Message {

    public EndTurnMessage(String username) {
        super(username, MessageType.END_TURN);
    }

    @Override
    public String toString() {
        return "EndTurnMessage{" +
                "username=" + getUsername() +
                '}';
    }

}
