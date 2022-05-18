package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.IntColorMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class ReducedPlayerBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = -7603520844991327878L;
    private int[] entranceStudents;
    private int[] hallStudents;
    private IntColorMap studentColorMap = new IntColorMap();
    private HashMap<Integer, Student> studentColor = studentColorMap.getMap();

    public ReducedPlayerBoard(int[] entranceStudents, int[] hallStudents) {
        this.entranceStudents = entranceStudents;
        this.hallStudents = hallStudents;
    }

    public void addEntranceStudent(){

    }

    public void removeEntranceStudent(){

    }

    public void addHallStudent(){

    }

    public void removeHallStudent(){

    }


}
