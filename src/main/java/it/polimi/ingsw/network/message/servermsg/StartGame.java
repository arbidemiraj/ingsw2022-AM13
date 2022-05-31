package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class StartGame extends Message {

    private String firstPlayer;
    private ReducedModel reducedModel;

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
