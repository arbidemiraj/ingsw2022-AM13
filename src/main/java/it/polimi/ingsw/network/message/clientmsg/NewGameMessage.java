package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

public class NewGameMessage extends Message {

    @Serial
    private static final long serialVersionUID = 5285990251901114080L;
    private final int maxPlayers;
    private final boolean expertMode;

    public NewGameMessage(String username, int maxPlayers, boolean expertMode) {
        super(username, MessageType.NEW_GAME);
        this.maxPlayers = maxPlayers;
        this.expertMode = expertMode;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    @Override
    public String toString() {
        return "NewGameMessage{" +
                "username=" + getUsername() +
                ", numPlayers=" + maxPlayers +
                ", expertMode=" + expertMode +
                '}';
    }
}
