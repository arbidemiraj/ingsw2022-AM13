package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class Effect12Message extends Message {
    private Student color;

    public Effect12Message(Student color) {
        super("Server", MessageType.EFFECT12);
        this.color = color;
    }

    public Student getColor() {
        return color;
    }
}
