package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

public class CharacterActivated extends Message {
    @Serial
    private static final long serialVersionUID = -3351832612563423675L;

    private int effectId;
    private boolean activated;

    public CharacterActivated(int effectId, boolean activated) {
        super("Server", MessageType.CHARACTER_ACTIVATED);
        this.effectId = effectId;
        this.activated = activated;
    }

    public int getEffectId() {
        return effectId;
    }

    public boolean isActivated() {
        return activated;
    }
}
