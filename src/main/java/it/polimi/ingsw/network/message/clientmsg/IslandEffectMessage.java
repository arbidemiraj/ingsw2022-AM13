package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server as a response of the AskIslandMessage
 */

public class IslandEffectMessage extends Message {

    @Serial
    private static final long serialVersionUID = -549964995352110878L;
    private final int chosenIslandId;
    private final int effectId;

    /**
     * Default constructor
     * @param username the username of the client
     * @param chosenIsland the id of the chosen island
     * @param effectId the id of the activated character
     */
    public IslandEffectMessage(String username,int chosenIsland, int effectId) {
        super(username, MessageType.ISLAND_EFFECT);
        this.chosenIslandId = chosenIsland;
        this.effectId = effectId;
    }

    public int getEffectId() {
        return effectId;
    }

    public int getChosenIslandId() {
        return chosenIslandId;
    }

    @Override
    public String toString() {
        return "IslandEffectMessage{" +
                "chosenIslandId=" + chosenIslandId +
                ", effectId=" + effectId +
                '}';
    }
}
