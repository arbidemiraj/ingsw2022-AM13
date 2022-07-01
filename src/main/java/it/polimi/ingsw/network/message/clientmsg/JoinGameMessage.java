package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 *  This message is sent from the client to the server when the user wants to join a game
 */

public class JoinGameMessage extends Message {

    @Serial
    private static final long serialVersionUID = 9187668166052235515L;
    private final int gameId;

    /**
     *
     * @param username
     * @param gameId The id the player wants to join
     */
    public JoinGameMessage(String username, int gameId) {
        super(username, MessageType.JOIN_GAME);
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "JoinGameMessage{" +
                "username=" + getUsername() +
                ", gameId=" + gameId +
                '}';
    }
}