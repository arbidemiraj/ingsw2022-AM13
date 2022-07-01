package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import javax.print.attribute.standard.Media;
import java.io.Serial;
import java.util.ArrayList;

/**
 * This message is sent from the server to the client to update the students of a character card
 */

public class UpdateCharacterStudents extends Message {

    @Serial
    private static final long serialVersionUID = 6690327810923476621L;

    private ArrayList<Student> students;
    private int effectId;

    /**
     * Default constructor
     * @param students the students on the card
     * @param effectId the id of the character card
     */

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
