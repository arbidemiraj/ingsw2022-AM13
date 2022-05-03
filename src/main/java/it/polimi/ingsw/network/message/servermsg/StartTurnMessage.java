package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class StartTurnMessage extends Message {
    public StartTurnMessage(){
        super("game", MessageType.START_TURN);
    }

    @Override
    public String toString() {
        return "action or preparation" + '}';
    }
}
