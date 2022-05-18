package it.polimi.ingsw.network.message.clientmsg;


import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the user wants to play an assistant card
 */


public class PlayCardMessage extends Message{

    private int assistantCard;

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
                "username=" + getUsername() +
                ", assistantcardId=" + assistantCard +
                '}';
    }
}
