package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server as a response of the AskStudentMessage
 */

public class StudentEffectMessage extends Message {

    @Serial
    private static final long serialVersionUID = 7785609495206580110L;
    private final Student chosenStudent;
    private final int effectId;

    public StudentEffectMessage(String username, Student chosenStudent, int effectId) {
        super(username, MessageType.STUDENT_EFFECT);
        this.chosenStudent = chosenStudent;
        this.effectId = effectId;
    }

    public int getEffectId() {
        return effectId;
    }

    public Student getChosenStudent() {
        return chosenStudent;
    }

    @Override
    public String toString() {
        return "StudentEffectMessage{" +
                "chosenStudent=" + chosenStudent +
                ", effectId=" + effectId +
                '}';
    }
}
