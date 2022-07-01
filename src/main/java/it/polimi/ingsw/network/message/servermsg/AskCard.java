package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * This message is sent from the server to the client to ask for a card to the player
 */
public class AskCard extends Message {
    @Serial
    private static final long serialVersionUID = -6135874046093059781L;

    private final List<AssistantCard> assistantCards;
    private final List<AssistantCard> cardsPlayed;

    /**
     * Default constructor
     * @param assistantCards list of the cards in the player's deck
     * @param cardsPlayed the cards that have been played in this turn
     */
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
