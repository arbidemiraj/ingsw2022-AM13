package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to ask the player for a tower color
 */
public class AskTowerColor extends Message {

    private static final long serialVersionUID = 3228204299244542883L;
    private ArrayList<TowerColor> availableColors;

    /**
     * Default constructor
     * @param availableColors the list of the available colors
     */
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
