package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ConfirmMoveStudent extends Message {
    private String from;
    private String to;
    private String student;
    private int id;

    public ConfirmMoveStudent(String from, String to, String student, int id) {
        super("Server", MessageType.CONFIRM_STUDENT_MOVE);

        this.from = from;
        this.to = student;
        this.student = student;
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getStudent() {
        return student;
    }

    public int getId() {
        return id;
    }
}
