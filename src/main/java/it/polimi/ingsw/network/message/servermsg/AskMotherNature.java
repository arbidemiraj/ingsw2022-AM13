package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to ask the player to move mother nature
 */
public class AskMotherNature extends Message {
    @Serial
    private static final long serialVersionUID = -3637970157819224255L;

    public AskMotherNature() {
        super("Server", MessageType.ASK_MN);
    }
}
