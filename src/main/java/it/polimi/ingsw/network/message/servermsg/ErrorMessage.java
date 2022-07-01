package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.ErrorType;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to notify an error
 */
public class ErrorMessage extends Message {

    @Serial
    private static final long serialVersionUID = -3862918113951268157L;
    private final String error;
    private final ErrorType errorType;

    /**
     * Default constructor
     * @param error the error
     * @param errorType the error's type
     */
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
