package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class AskStudent extends Message {

    private static final long serialVersionUID = -4015943428142654524L;
    private ArrayList<Student> students;

    public AskStudent() {
        super("server", MessageType.ASK_STUDENT);
    }

    @Override
    public String toString() {
        return "AskStudent{" +
                '}';
    }
}
