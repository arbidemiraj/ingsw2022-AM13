package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
 * This message is sent from the server to the client for a generic information
 */
public class GenericMessage extends Message{

    @Serial
    private static final long serialVersionUID = 1109494532898309569L;
    private final String toPrint;
    private final GenericType genericType;


    public GenericMessage(String toPrint, GenericType genericType) {
        super("Server", MessageType.GENERIC);
        this.genericType = genericType;
        this.toPrint = toPrint;
    }

    @Override
    public String toString() {
        return toPrint;
    }
}
