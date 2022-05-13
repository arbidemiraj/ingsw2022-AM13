package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ErrorMessage extends Message {

    private String error;
    
    public ErrorMessage(String error) {
        super("Server", MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return error;
    }
}
