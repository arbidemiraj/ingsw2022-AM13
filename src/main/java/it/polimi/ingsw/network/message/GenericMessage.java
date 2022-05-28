package it.polimi.ingsw.network.message;

public class GenericMessage extends Message{
    private final String toPrint;
    private final GenericType genericType;


    public GenericMessage(String toPrint, GenericType genericType) {
        super("Server", MessageType.GENERIC);
        this.genericType = genericType;
        this.toPrint = toPrint;
    }

    public GenericType getGenericType() {
        return genericType;
    }

    @Override
    public String toString() {
        return toPrint;
    }
}
