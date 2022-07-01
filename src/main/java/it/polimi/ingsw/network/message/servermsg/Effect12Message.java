package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the server to the client to notify the activation of the character 12 and the remove of the students
 */
public class Effect12Message extends Message {

    @Serial
    private static final long serialVersionUID = 4868234089910404045L;
    private final Student color;

    /**
     * Default constructor
     * @param color the color of the removed students
     */
    public Effect12Message(Student color) {
        super("Server", MessageType.EFFECT12);
        this.color = color;
    }

    public Student getColor() {
        return color;
    }
}
