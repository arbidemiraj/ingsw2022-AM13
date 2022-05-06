package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskTowerColor extends Message {

    private static final long serialVersionUID = 3228204299244542883L;
    public AskTowerColor() {
        super("server", MessageType.TOWER_COLOR_ASK);
    }

    @Override
    public String toString() {
        return "Insert the tower color you want \nChoices: [BLACK] | [GRAY] | [WHITE]";
    }
}
