package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class CloudMessage extends Message {
    private int cloudId;
    public CloudMessage(String username, int cloudId) {
        super(username, MessageType.CLOUD);

        this.cloudId = cloudId;
    }

    public int getCloudId() {
        return cloudId;
    }

    @Override
    public String toString() {
        return "CloudMessage{" +
                "username=" + getUsername() +
                "cloudId=" + cloudId +
                '}';
    }
}
