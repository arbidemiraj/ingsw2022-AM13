package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to a client to notify that a no entry tile has been removed from an island
 */
public class NoEntryTileMessage extends Message {
    @Serial
    private static final long serialVersionUID = 2009253434171421196L;
    private final int islandId;

    public NoEntryTileMessage(int islandId) {
        super("Server", MessageType.NO_ENTRY_TILE);
        this.islandId = islandId;
    }

    public int getIslandId() {
        return islandId;
    }
}
