package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class NotifyDisconnectionMessage extends Message {
    private String username;

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
