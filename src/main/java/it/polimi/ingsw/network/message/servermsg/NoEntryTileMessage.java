package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class NoEntryTileMessage extends Message {
    private final int islandId;

    public NoEntryTileMessage(int islandId) {
        super("Server", MessageType.NO_ENTRY_TILE);
        this.islandId = islandId;
    }

    public int getIslandId() {
        return islandId;
    }
}
