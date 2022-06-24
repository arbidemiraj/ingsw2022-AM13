package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class UpdateMotherNature extends Message {
    private int motherNature;

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
