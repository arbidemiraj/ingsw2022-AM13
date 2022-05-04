package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * The player choose the cloud he wants to get the students from
 */

public class ChooseCloudMessage extends Message {
    private int cloudId;
    public ChooseCloudMessage(String username, int cloudId) {
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
