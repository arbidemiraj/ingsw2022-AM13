package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * Message to notify that a player has won the game.
 */
public class WinMessage extends Message {

    @Serial
    private static final long serialVersionUID = 7271062647050517622L;

    public WinMessage() {
        super("Server", MessageType.WIN);
    }

    @Override
    public String toString() {
        return null;
    }
}