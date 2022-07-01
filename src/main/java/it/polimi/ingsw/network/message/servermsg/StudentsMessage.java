package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

/**
 * This message is sent from the server to the client to ask the student to activate the character card
 */
public class StudentsMessage extends Message {

    @Serial
    private static final long serialVersionUID = 8007657481226132061L;
    private final ArrayList<Student> students;
    private final int effectId;

    /**
     * Default constructor
     * @param students the students on the card
     * @param effectId the id of the character card
     */
    public StudentsMessage(ArrayList<Student> students, int effectId) {
        super("Server", MessageType.STUDENTS);
        this.students = students;

        this.effectId = effectId;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getEffectId() {
        return effectId;
    }
}
