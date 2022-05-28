package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ConfirmCharacterActivation extends Message {
    private int id;

    public ConfirmCharacterActivation(int id) {
        super("Server", MessageType.CONFIRM_CHARACTER);

        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ConfirmCharacterActivation{" +
                "id=" + id +
                '}';
    }
}
