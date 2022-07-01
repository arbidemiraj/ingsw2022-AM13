package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to notify the start of the game
 */
public class StartGame extends Message {

    @Serial
    private static final long serialVersionUID = 2189054633485982524L;
    private final String firstPlayer;
    private final ReducedModel reducedModel;

    /**
     * Default constructor
     * @param firstPlayer the username of the first player
     * @param reducedModel the lighter version of the model the client will have
     */
    public StartGame(String firstPlayer, ReducedModel reducedModel) {
        super("Server", MessageType.START_GAME);

        this.reducedModel = reducedModel;
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public ReducedModel getReducedModel() {
        return reducedModel;
    }

    @Override
    public String toString() {
        return "StartGame{" +
                "firstPlayer='" + firstPlayer + '\'' +
                '}';
    }
}
