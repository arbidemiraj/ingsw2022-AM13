package it.polimi.ingsw.network.message.clientmsg;


import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server when the user wants to play an assistant card
 */

public class PlayCardMessage extends Message{

    @Serial
    private static final long serialVersionUID = 1217519339922244295L;
    private final int assistantCard;

    /**
     *
     * @param username
     * @param assistantCardId the id of the card the player wants to play
     */
    public PlayCardMessage(String username, int assistantCardId ) {
        super(username, MessageType.PLAY_CARD);
        this.assistantCard = assistantCardId;
    }

    public int getAssistantCard() {
        return assistantCard;
    }

    @Override
    public String toString() {
        return "PlayCardMessage{" +
                "assistantCard=" + assistantCard +
                '}';
    }
}
