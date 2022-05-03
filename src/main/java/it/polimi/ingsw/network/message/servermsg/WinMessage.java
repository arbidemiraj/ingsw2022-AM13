package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * Message to notify that a player has won the game.
 */
public class WinMessage extends Message {

    @Serial
    private static final long serialVersionUID = 7271062647050517622L;
    private final String winnerUsername;
    
    public WinMessage(String winnerUsername) {
        super("Server", MessageType.WIN);
        this.winnerUsername = winnerUsername;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }
    
    @Override
    public String toString() {
        return "WinMessage{" +
                "username=" + getUsername() +
                ", winnerUsername=" + winnerUsername +
                '}';
    }
}