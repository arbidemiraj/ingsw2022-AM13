package it.polimi.ingsw.network.message;

public class SuccessMessage extends Message{

    public SuccessMessage() {
        super("server", MessageType.SUCCESS);
    }

    @Override
    public String toString() {
        return "Operation successfully completed";
    }
}
