package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskSwitchStudent extends Message {
    public AskSwitchStudent() {
        super("Server", MessageType.ASK_SWITCH_STUDENT);
    }

    @Override
    public String toString() {
        return "AskSwitchStudent{}";
    }
}
