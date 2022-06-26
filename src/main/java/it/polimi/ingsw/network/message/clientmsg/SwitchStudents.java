package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

public class SwitchStudents extends Message {
    @Serial
    private static final long serialVersionUID = -4865678587900765629L;
    private final ArrayList<Student> students;
    private final int effectId;

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
