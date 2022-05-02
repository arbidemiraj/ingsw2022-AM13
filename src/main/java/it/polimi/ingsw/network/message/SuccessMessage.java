package it.polimi.ingsw.network.message;

public class SuccessMessage extends Message{

    public SuccessMessage(String username) {
        super(username, MessageType.SUCCESS);
    }

    @Override
    public String toString() {
        return "Operation successfully completed";
    }
}
