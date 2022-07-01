package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to ask for the game settings when creating a new game
 */
public class AskGameSettings extends Message {

    @Serial
    private static final long serialVersionUID = -1142083927334749138L;

    public AskGameSettings() {
        super("Server", MessageType.ASK_GAME_SETTINGS);
    }

    @Override
    public String toString() {
        return "AskGameSettings{}";
    }
}
