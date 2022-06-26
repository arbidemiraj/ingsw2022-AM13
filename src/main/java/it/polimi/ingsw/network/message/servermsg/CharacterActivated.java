package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

public class CharacterActivated extends Message {
    @Serial
    private static final long serialVersionUID = -3351832612563423675L;

    private final int effectId;
    private final boolean activated;
    private final String owner;

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
