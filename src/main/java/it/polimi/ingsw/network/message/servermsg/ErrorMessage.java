package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.ErrorType;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ErrorMessage extends Message {

    private String error;
    private final ErrorType errorType;

    public ErrorMessage(String error, ErrorType errorType) {
        super("Server", MessageType.ERROR);
        this.error = error;
        this.errorType = errorType;
    }

    public ErrorType getErrorType(){
        return errorType;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return error;
    }
}
