package it.polimi.ingsw.network.message;

public class ChooseMessage extends Message{

    public ChooseMessage(String username) {
        super(username, MessageType.CHOOSE);
    }

    @Override
    public String toString() {
        return "\n [1] to create a new game \n [2] to join a game \n [3] to reload a game";
    }
}
