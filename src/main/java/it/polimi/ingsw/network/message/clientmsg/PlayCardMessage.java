package it.polimi.ingsw.network.message.clientmsg;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the user wants to play an assistant card
 */


public class PlayCardMessage extends Message{

    private int assistantcardId;

    public PlayCardMessage(String username, int assistantcardId ) {
        super(username, MessageType.PLAY_CARD);
        this.assistantcardId = assistantcardId;
    }

    public int getAssistantcardId() {
        return assistantcardId;
    }

    @Override
    public String toString() {
        return "PlayCardMessage{" +
                "username=" + getUsername() +
                ", assistantcardId=" + assistantcardId +
                '}';


    }
}
