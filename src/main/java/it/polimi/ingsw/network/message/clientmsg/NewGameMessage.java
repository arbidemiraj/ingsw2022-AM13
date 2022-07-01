package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server when a player wants to create a new game
 */
public class NewGameMessage extends Message {

    @Serial
    private static final long serialVersionUID = 5285990251901114080L;
    private final int maxPlayers;
    private final boolean expertMode;

    /**
     *
     * @param username
     * @param maxPlayers the number of players in the game
     * @param expertMode true if the games is in expert mode
     */
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
