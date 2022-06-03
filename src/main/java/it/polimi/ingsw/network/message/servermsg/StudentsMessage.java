package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class StudentsMessage extends Message {
    private ArrayList<Student> students;
    private int effectId;

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
