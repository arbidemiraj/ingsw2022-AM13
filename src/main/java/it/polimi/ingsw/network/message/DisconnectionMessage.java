package it.polimi.ingsw.network.message;

public class DisconnectionMessage extends Message {


    public DisconnectionMessage(String username) {
        super(username, MessageType.DISCONNECTED);
    }

    @Override
    public String toString() {
        return getUsername() +
                "got disconnected" +
                '}';
    }
}
