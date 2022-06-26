package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * The player  the cloud he wants to get the students from
 */

public class ChooseCloudMessage extends Message {
    @Serial
    private static final long serialVersionUID = 1382200608249419815L;
    private final int cloudId;

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
