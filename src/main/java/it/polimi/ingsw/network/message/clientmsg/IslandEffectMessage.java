package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server as a response of the AskIslandMessage
 */


public class IslandEffectMessage extends Message {

    private Island chosenIsland;
    public IslandEffectMessage(String username,Island chosenIsland) {
        super(username, MessageType.ISLAND_EFFECT);
        this.chosenIsland = chosenIsland;
    }

    public Island getChosenIsland() {
        return chosenIsland;
    }

    @Override
    public String toString() {
        return "IslandEffectMessage{" +
                "username=" + getUsername() +
                ", chosenIsland=" + chosenIsland +
                '}';


    }
}
