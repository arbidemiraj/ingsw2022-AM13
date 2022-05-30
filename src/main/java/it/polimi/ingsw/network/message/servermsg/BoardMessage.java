package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedIsland;
import it.polimi.ingsw.network.client.reducedModel.ReducedPlayerBoard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

public class BoardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 4789603790274025626L;

    private ReducedBoard reducedBoard;

    public BoardMessage(Cloud[] clouds, String[] owner, ReducedPlayerBoard reducedPlayerBoard, int motherNature, ArrayList<ReducedIsland> islands) {
        super("Server", MessageType.BOARD_MESSAGE);
        this.reducedBoard = new ReducedBoard(clouds, owner, reducedPlayerBoard, motherNature, islands);
    }

    public ReducedBoard getReducedBoard() {
        return reducedBoard;
    }

    @Override
    public String toString() {
        return "BoardMessage{" +
                "reducedBoard=" + reducedBoard +
                '}';
    }
}
