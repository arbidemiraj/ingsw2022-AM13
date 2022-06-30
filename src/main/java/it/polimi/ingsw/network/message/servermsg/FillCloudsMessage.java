package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.awt.*;
import java.util.Arrays;

public class FillCloudsMessage extends Message {
    private Cloud[] clouds;

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
