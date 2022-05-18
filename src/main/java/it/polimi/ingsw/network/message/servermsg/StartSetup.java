package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class StartSetup extends Message {
    public StartSetup() {
        super("Server", MessageType.START_SETUP);
    }

    @Override
    public String toString() {
        return "StartSetup{}";
    }
}
