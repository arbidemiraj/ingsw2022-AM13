package it.polimi.ingsw.network.message;

public class SuccessMessage extends Message{

    public SuccessMessage(String nickname) {
        super(nickname, MessageType.SUCCESS);
    }

    @Override
    public String toString() {
        return "Operation successfully completed";
    }
}
