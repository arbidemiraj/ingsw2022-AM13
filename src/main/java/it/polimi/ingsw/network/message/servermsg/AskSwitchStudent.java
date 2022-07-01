package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to the player for effects that needs to switch students positions
 */
public class AskSwitchStudent extends Message {
    @Serial
    private static final long serialVersionUID = 5218842645298460353L;

    public AskSwitchStudent() {
        super("Server", MessageType.ASK_SWITCH_STUDENT);
    }

    @Override
    public String toString() {
        return "AskSwitchStudent{}";
    }
}
