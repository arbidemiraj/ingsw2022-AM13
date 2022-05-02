package it.polimi.ingsw.network.message;

public class EndTurnMessage extends Message {

    public EndTurnMessage(String username) {
        super(username, MessageType.END_TURN)
    }

    @Override
    public String toString() {
        return "EndTurnMessage{" +
                "username=" + getUsername() +
                '}';
    }

}
