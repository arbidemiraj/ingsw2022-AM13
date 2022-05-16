package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ChooseMessage extends Message {

    public ChooseMessage() {
        super("Server", MessageType.CHOOSE_GAME_OPTIONS);
    }

    @Override
    public String toString() {
        return "\n [1] to create a new game \n [2] to join a game";
    }
}
