package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the user wants to buy a character
 */

public class ActivateCharacterMessage extends Message {

    private int effectId;

    public ActivateCharacterMessage(String username,int effectId) {
        super(username, MessageType.ACTIVATE_CHARACTER);
        this.effectId = effectId;
    }

    public int getEffectId() {
        return effectId;
    }

    @Override
    public String toString() {
        return "ActivateCharacterMessage{" +
                "username=" + getUsername() +
                ", effectId=" + effectId +
                '}';


    }
}
