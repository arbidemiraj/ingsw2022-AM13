package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to update mother nature
 */
public class UpdateMotherNature extends Message {

    @Serial
    private static final long serialVersionUID = -7220413088379595346L;

    private final int motherNature;

    /**
     *
     * @param motherNature the updated mother nature
     */
    public UpdateMotherNature(int motherNature) {
        super("Server", MessageType.UPDATE_MOTHERNATURE);
        this.motherNature = motherNature;
    }

    public int getMotherNature() {
        return motherNature;
    }

    @Override
    public String toString() {
        return "UpdateMotherNature{" +
                "motherNature=" + motherNature +
                '}';
    }
}
