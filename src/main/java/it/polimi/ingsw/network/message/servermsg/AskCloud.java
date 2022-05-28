package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class AskCloud extends Message {
    private Cloud[] clouds;

    public AskCloud(Cloud[] clouds) {
        super("Server", MessageType.ASK_CLOUD);

        this.clouds = clouds;
    }

    public Cloud[] getClouds() {
        return clouds;
    }
}
