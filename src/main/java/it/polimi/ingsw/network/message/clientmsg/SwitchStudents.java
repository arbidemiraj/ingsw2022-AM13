package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

/**
 * This message is sent from the client to the server to activate the characters 7 or 10
 */
public class SwitchStudents extends Message {
    @Serial
    private static final long serialVersionUID = -4865678587900765629L;
    private final ArrayList<Student> students;
    private final int effectId;

    /**
     *
     * @param username
     * @param students list of the switched students
     * @param effectId the id of the character card
     */
    public SwitchStudents(String username, ArrayList<Student> students, int effectId) {
        super(username, MessageType.SWITCH_STUDENTS);
        this.students = students;
        this.effectId = effectId;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getEffectId() {
        return effectId;
    }

    @Override
    public String toString() {
        return "SwitchStudents{" +
                "students=" + students +
                '}';
    }
}
