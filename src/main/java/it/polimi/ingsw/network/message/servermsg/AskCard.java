package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class AskCard extends Message {
    private final List<AssistantCard> assistantCards;


    public AskCard(List<AssistantCard> assistantCards) {
        super("Server", MessageType.ASK_CARD);
        this.assistantCards = assistantCards;
    }

    public List<AssistantCard> getAssistantCards() {
        return assistantCards;
    }

    @Override
    public String toString() {
        return "AskCard{" +
                "assistantCards=" + assistantCards +
                '}';
    }
}
