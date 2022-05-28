package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

/**
 * This message is sent from the client to the server when the user wants to move a student
 */


public class MoveStudentMessage extends Message {

    private String from;
    private String color;
    private String to;
    private int id;


    public MoveStudentMessage(String username, String from, String color, String to, int id) {
        super(username, MessageType.MOVE_STUDENT);
        this.from = from;
        this.color = color;
        this.to = to;
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public String getColor() {
        return color;
    }

    public String getTo() {
        return to;
    }

    public int getId() {
        return id;
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