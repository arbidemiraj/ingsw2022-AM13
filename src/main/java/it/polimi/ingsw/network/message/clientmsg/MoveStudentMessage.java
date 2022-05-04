package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the user wants to move a student
 */


public class MoveStudentMessage extends Message {

    private Movable from;
    private Student color;
    private Movable to;

    public MoveStudentMessage(String username, Movable from, Student color, Movable to) {
        super(username, MessageType.MOVE_STUDENT);
        this.from = from;
        this.color = color;
        this.to = to;
    }

    public Movable getFrom() {
        return from;
    }

    public Student getColor() {
        return color;
    }

    public Movable getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "MoveStudentMessage{" +
                "username=" + getUsername() +
                ", from=" + from +
                ", color=" + color +
                ", to=" + to +
                '}';
    }

}