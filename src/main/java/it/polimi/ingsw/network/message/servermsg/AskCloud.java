package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

/**
 * This message is sent from the server to the client to ask for a cloud to unfill
 */
public class AskCloud extends Message {
    @Serial
    private static final long serialVersionUID = -4263620654328850119L;

    private Cloud[] clouds;

    /**
     * Default constructor
     * @param clouds the clouds in the game
     */
    public AskCloud(Cloud[] clouds) {
        super("Server", MessageType.ASK_CLOUD);

        this.clouds = clouds;
    }

    public Cloud[] getClouds() {
        return clouds;
    }
}
