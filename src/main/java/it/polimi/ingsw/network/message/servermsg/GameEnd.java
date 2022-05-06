package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message to notify that the game has ended.
 */
public class GameEnd extends Message {
    private static final long serialVersionUID = 930800675956106532L;
    public GameEnd() {
        super("server", MessageType.END_GAME);
    }

    @Override
    public String toString() {
        return "The game has ended";
    }
}