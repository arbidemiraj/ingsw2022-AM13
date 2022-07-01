package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.awt.*;
import java.io.Serial;
import java.util.Arrays;

/**
 * This message is sent from the client to the server to notify that the cloud have been filled
 */
public class FillCloudsMessage extends Message {

    @Serial
    private static final long serialVersionUID = -8908113438255107699L;
    private final Cloud[] clouds;

    /**
     * Default constructor
     * @param clouds the clouds from the game board
     */
    public FillCloudsMessage(Cloud[] clouds) {
        super("Server", MessageType.FILL_CLOUDS);
        this.clouds = clouds;
    }

    public Cloud[] getClouds() {
        return clouds;
    }

    @Override
    public String toString() {
        return "FillCloudsMessage{" +
                "clouds=" + Arrays.toString(clouds) +
                '}';
    }
}
