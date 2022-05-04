package it.polimi.ingsw.network.message.clientmsg;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the player wants to move mother nature
 */

public class MoveMotherNatureMessage extends Message{

    private int steps;

    public MoveMotherNatureMessage(String username, int steps) {
        super(username, MessageType.MOVE_MOTHERNATURE);
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }


    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "username=" + getUsername() +
                ", steps=" + steps +
                '}';
    }
}
