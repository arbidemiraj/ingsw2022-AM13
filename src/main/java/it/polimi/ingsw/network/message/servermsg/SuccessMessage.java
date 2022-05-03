package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class SuccessMessage extends Message {

    public SuccessMessage() {
        super("server", MessageType.SUCCESS);
    }

    @Override
    public String toString() {
        return "Operation successfully completed";
    }
}
