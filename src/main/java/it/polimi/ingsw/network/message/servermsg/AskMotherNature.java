package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskMotherNature extends Message {
    public AskMotherNature() {
        super("Server", MessageType.ASK_MN);
    }
}
