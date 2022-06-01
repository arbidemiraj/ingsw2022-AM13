package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.UpdateType;

import java.util.List;

public class UpdateModelMessage extends Message {
    private List<AssistantCard> turnCardsPlayed;
    private int maxSteps;
    private UpdateType updateType;

    public UpdateModelMessage(List<AssistantCard> turnCardsPlayed) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.TURN_CARDS;
        this.turnCardsPlayed = turnCardsPlayed;
    }

    public UpdateModelMessage(int maxSteps) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.MAX_STEPS;
        this.maxSteps = maxSteps;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public List<AssistantCard> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }

    @Override
    public String toString() {
        return "UpdateModelMessage{" +
                "updateType=" + updateType +
                '}';
    }
}
