package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to notify that a cloud has been unfilled
 */
public class UpdateClouds extends Message {
    @Serial
    private static final long serialVersionUID = 4588561731052857459L;

    private final int cloudId;

    /**
     *
     * @param cloudId the id of the cloud that has been unfilled
     */
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
