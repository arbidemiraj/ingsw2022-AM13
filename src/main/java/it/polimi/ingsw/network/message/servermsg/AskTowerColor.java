package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskTowerColor extends Message {
    public AskTowerColor(String username) {
        super(username, MessageType.TOWER_COLOR_ASK);
    }

    @Override
    public String toString() {
        return "Insert the tower color you want \nChoices: [BLACK] | [GRAY] | [WHITE]";
    }
}
