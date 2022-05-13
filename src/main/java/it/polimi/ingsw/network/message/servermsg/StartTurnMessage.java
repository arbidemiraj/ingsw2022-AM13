package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class StartTurnMessage extends Message {
    public StartTurnMessage(){
        super("server", MessageType.START_TURN);
    }

    @Override
    public String toString() {
        return "it's your turn";
    }
}
