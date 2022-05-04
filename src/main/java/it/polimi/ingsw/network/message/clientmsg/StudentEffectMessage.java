package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server as a response of the AskStudentMessage
 */

public class StudentEffectMessage extends Message {

    private Student chosenStudent;

    public StudentEffectMessage(String username,Student chosenStudent) {
        super(username, MessageType.STUDENT_EFFECT);
        this.chosenStudent = chosenStudent;
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
