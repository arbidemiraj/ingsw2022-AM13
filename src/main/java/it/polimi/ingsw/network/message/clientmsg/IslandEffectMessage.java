package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server as a response of the AskIslandMessage
 */


public class IslandEffectMessage extends Message {

    private int chosenIslandId;
    private int effectId;

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
                "username=" + getUsername() +
                ", chosenIsland=" + chosenIslandId +
                '}';


    }
}
