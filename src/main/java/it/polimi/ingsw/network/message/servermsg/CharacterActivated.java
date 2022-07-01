package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to notify that a character has been activated
 */
public class CharacterActivated extends Message {
    @Serial
    private static final long serialVersionUID = -3351832612563423675L;

    private final int effectId;
    private final boolean activated;
    private final String owner;

    /**
     * Default constructor
     * @param effectId the id of the activated / no more active effect
     * @param activated true if the effect is being activated
     * @param owner the username of the player who activated the effect
     */
    public CharacterActivated(int effectId, boolean activated, String owner) {
        super("Server", MessageType.CHARACTER_ACTIVATED);
        this.owner = owner;
        this.effectId = effectId;
        this.activated = activated;
    }

    public int getEffectId() {
        return effectId;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "CharacterActivated{" +
                "effectId=" + effectId +
                ", activated=" + activated +
                ", owner='" + owner + '\'' +
                '}';
    }
}
