package it.polimi.ingsw.network.message;

public class ErrorMessage extends Message {

    private String error;
    
    public ErrorMessage(String username, String error) {
        super(username, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
