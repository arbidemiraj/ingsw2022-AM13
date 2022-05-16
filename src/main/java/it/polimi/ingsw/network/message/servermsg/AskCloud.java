package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskCloud extends Message {
    public AskCloud() {
        super("Server", MessageType.ASK_CLOUD);
    }
}
