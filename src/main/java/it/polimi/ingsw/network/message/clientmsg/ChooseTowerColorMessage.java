package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server
 */
public class ChooseTowerColorMessage extends Message {

    @Serial
    private static final long serialVersionUID = 1986386864618069042L;
    private final TowerColor chosenTowerColor;

    public ChooseTowerColorMessage(String username, TowerColor chosenTowerColor) {
        super(username, MessageType.TOWER_COLOR_CHOOSE);
        this.chosenTowerColor = chosenTowerColor;
    }

    public TowerColor getChosenTowerColor() {
        return chosenTowerColor;
    }

    @Override
    public String toString() {
        return "ChooseTowerColorMessage{" +
                "chosenTowerColor=" + chosenTowerColor +
                '}';
    }
}
