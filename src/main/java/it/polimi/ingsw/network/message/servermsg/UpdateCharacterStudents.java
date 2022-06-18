package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;

public class UpdateCharacterStudents extends Message {
    private ArrayList<Student> students;
    private int effectId;

    public UpdateCharacterStudents(ArrayList<Student> students, int effectId) {
        super("Server", MessageType.UPDATE_CHARACTER_STUDENTS);
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
