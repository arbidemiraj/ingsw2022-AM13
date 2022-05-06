package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class MoveStudent extends Message {

    private static final long serialVersionUID = -4015943428142654524L;

    public MoveStudent() {
        super("server", MessageType.MOVE_STUDENT);
    }

    @Override
    public String toString() {
        return "Insert the colour of the student you want to move and where";
    }
}
