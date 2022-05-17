package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class AskCard extends Message {
    private final List<AssistantCard> assistantCards;
    private final List<AssistantCard> cardsPlayed;

    public AskCard(List<AssistantCard> assistantCards, List<AssistantCard> cardsPlayed) {
        super("Server", MessageType.ASK_CARD);
        this.assistantCards = assistantCards;
        this.cardsPlayed = cardsPlayed;
    }

    public List<AssistantCard> getAssistantCards() {
        return assistantCards;
    }

    public List<AssistantCard> getCardsPlayed() {
        return cardsPlayed;
    }

    @Override
    public String toString() {
        return "AskCard{" +
                "assistantCards=" + assistantCards +
                '}';
    }
}
