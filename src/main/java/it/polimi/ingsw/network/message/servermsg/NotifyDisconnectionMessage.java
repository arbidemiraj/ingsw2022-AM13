package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to notify a disconnection of a player
 */
public class NotifyDisconnectionMessage extends Message {
    @Serial
    private static final long serialVersionUID = -6188742548928767173L;
    private final String username;

    /**
     * Default constructor
     * @param username the username of the disconnected player
     */
    public NotifyDisconnectionMessage(String username) {
        super("Server", MessageType.NOTIFY_DISCONNECTION);
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "NotifyDisconnectionMessage{" +
                "username='" + username + '\'' +
                '}';
    }
}
