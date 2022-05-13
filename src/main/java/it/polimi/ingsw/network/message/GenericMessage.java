package it.polimi.ingsw.network.message;

public class GenericMessage extends Message{
    private final String toPrint;

    public GenericMessage(String toPrint) {
        super("Server", MessageType.GENERIC);
        this.toPrint = toPrint;
    }

    @Override
    public String toString() {
        return toPrint;
    }
}
