package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server when the user wants to move a student
 */


public class MoveStudentMessage extends Message {

    @Serial
    private static final long serialVersionUID = -3060931431714572679L;
    private final String from;
    private final String color;
    private final String to;
    private final int id;


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
                "from='" + from + '\'' +
                ", color='" + color + '\'' +
                ", to='" + to + '\'' +
                ", id=" + id +
                '}';
    }
}