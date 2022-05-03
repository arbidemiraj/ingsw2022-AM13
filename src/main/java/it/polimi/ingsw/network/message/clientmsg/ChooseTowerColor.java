package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ChooseTowerColor extends Message {
    private TowerColor chosenTowerColor;

    public ChooseTowerColor(String username, TowerColor chosenTowerColor) {
        super(username, MessageType.TOWER_COLOR_CHOOSE);
        this.chosenTowerColor = chosenTowerColor;
    }

    public TowerColor getChosenTowerColor() {
        return chosenTowerColor;
    }

    @Override
    public String toString() {
        return "ChooseTowerColorMessage{" +
                "username="+ getUsername()+
                "chosenTowerColor=" + chosenTowerColor;
    }
}
