package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server as a response of the AskStudentMessage
 */

public class StudentEffectMessage extends Message {

    private Student chosenStudent;
    private int effectId;

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
                "username=" + getUsername() +
                ", chosenStudent=" + chosenStudent +
                '}';


    }
}
