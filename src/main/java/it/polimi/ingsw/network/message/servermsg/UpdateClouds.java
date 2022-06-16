package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class UpdateClouds extends Message {
    private int cloudId;

    public UpdateClouds(int cloudId) {
        super("Server", MessageType.UPDATE_CLOUD);
        this.cloudId = cloudId;
    }

    public int getCloudId() {
        return cloudId;
    }

    @Override
    public String toString() {
        return "UpdateClouds{" +
                "cloudId=" + cloudId +
                '}';
    }
}
