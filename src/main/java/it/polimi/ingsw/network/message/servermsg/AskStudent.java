package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

/**
 * This message is sent from the server to the client to ask the user to move a student
 */
public class AskStudent extends Message {

    @Serial
    private static final long serialVersionUID = -4015943428142654524L;

    public AskStudent() {
        super("server", MessageType.ASK_STUDENT);
    }

    @Override
    public String toString() {
        return "AskStudent{" +
                '}';
    }
}
