package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class TurnInfo extends Message {
    private int maxSteps;

    public TurnInfo(int maxSteps) {
        super("Server", MessageType.TURN_INFO);
        this.maxSteps = maxSteps;
    }

    public int getSteps() {
        return maxSteps;
    }
}
