package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.SuccessType;

public class SuccessMessage extends Message {

    private SuccessType successType;

    public SuccessMessage(SuccessType successType) {
        super("server", MessageType.SUCCESS);

        this.successType = successType;
    }
    
    public SuccessType getSuccessType() {
        return successType;
    }

    @Override
    public String toString() {
        return "Operation successfully completed";
    }
}
