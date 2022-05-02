package it.polimi.ingsw.network.message;

/**
 * The player wants to reload a game that recently went down
 */

public class ReloadGameMessage extends Message{


    public ReloadGameMessage(String username) {
        super(username, MessageType.RELOAD_GAME);
    }


    @Override
    public String toString() {
        return "ReloadGameMessage{" +
                "username=" + getUsername() +
                '}';

    }

}