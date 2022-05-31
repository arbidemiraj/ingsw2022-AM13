package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedCharacter;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReducedModelMessage extends Message {

    @Serial
    private static final long serialVersionUID = -5661595295706856012L;
    private ReducedModel reducedModel;

    public ReducedModelMessage(List<String> username, TowerColor color, ReducedCharacter[] reducedCharacters, ReducedBoard reducedBoard, boolean isExpertMode) {
        super("Server", MessageType.REDUCED_MODEL);
        this.reducedModel = new ReducedModel(username,color, reducedCharacters, reducedBoard, isExpertMode);
    }

    public ReducedModelMessage(List<String> username, TowerColor color, ReducedBoard reducedBoard) {
        super("Server", MessageType.REDUCED_MODEL);
        this.reducedModel = new ReducedModel(username,color, reducedBoard);
    }

    public ReducedModel getReducedModel() {
        return reducedModel;
    }
}
