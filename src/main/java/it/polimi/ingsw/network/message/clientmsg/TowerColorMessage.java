package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class TowerColorMessage extends Message {

    private TowerColor chosenTowerColor;

    public TowerColorMessage(String username, TowerColor chosenTowerColor) {
        super(username, MessageType.TOWER_COLOR);

        this.chosenTowerColor = chosenTowerColor;
    }

    public TowerColor getChosenTowerColor() {
        return chosenTowerColor;
    }

    @Override
    public String toString() {
        return "TowerColorMessage";
    }
}
