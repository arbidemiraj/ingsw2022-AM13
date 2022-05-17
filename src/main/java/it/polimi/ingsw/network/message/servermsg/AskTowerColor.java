package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class AskTowerColor extends Message {

    private static final long serialVersionUID = 3228204299244542883L;
    private ArrayList<TowerColor> availableColors;

    public AskTowerColor(ArrayList<TowerColor> availableColors) {
        super("server", MessageType.TOWER_COLOR_ASK);
        this.availableColors = availableColors;
    }

    public ArrayList<TowerColor> getAvailableColors() {
        return availableColors;
    }

    @Override
    public String toString() {
        return "Insert the tower color you want \nChoices: [BLACK] | [GRAY] | [WHITE]";
    }
}
