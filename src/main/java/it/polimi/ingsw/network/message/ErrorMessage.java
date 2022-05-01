package it.polimi.ingsw.network.message;

public class ErrorMessage extends Message {

    private String error;
    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
