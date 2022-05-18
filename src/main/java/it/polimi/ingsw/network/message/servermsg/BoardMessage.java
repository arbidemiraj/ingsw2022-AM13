package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class BoardMessage extends Message {
    private ReducedBoard reducedBoard;

    public BoardMessage(GameBoard gameBoard) {
        super("Server", MessageType.BOARD_MESSAGE);
        this.reducedBoard = new ReducedBoard(gameBoard);
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
