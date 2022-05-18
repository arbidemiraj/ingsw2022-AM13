package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskStudent extends Message {

    private static final long serialVersionUID = -4015943428142654524L;
    private int numStudent;
    public AskStudent(int numStudent) {
        super("server", MessageType.ASK_STUDENT);
        this.numStudent = numStudent;
    }

    @Override
    public String toString() {
        return "Choose a student";
    }
}
