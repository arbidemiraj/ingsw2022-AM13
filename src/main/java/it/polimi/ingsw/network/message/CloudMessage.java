package it.polimi.ingsw.network.message;

public class CloudMessage extends Message{
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
