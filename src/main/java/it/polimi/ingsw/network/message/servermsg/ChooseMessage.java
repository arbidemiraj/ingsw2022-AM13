package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to ask the player if he wants to join or create a game
 */
public class ChooseMessage extends Message {

    @Serial
    private static final long serialVersionUID = 5263241322187508175L;

    public ChooseMessage() {
        super("Server", MessageType.CHOOSE_GAME_OPTIONS);
    }

    @Override
    public String toString() {
        return "ChooseMessage{}";
    }
}
