package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * Message to notify that a player has won the game.
 */
public class WinMessage extends Message {

    @Serial
    private static final long serialVersionUID = -2178844112313440462L;
    private final String winner;

    /**
     *
     * @param winner the username of the winner
     */
    public WinMessage(String winner) {
        super("Server", MessageType.WIN);
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "WinMessage{" +
                "winner='" + winner + '\'' +
                '}';
    }
}