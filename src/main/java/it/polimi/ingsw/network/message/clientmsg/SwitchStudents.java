package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class SwitchStudents extends Message {
    private ArrayList<Student> students;

    public SwitchStudents(String username, ArrayList<Student> students) {
        super(username, MessageType.SWITCH_STUDENTS);
        this.students = students;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
