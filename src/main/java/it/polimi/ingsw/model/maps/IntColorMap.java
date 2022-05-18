package it.polimi.ingsw.model.maps;

import it.polimi.ingsw.model.enumerations.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class IntColorMap implements Serializable {
    @Serial
    private static final long serialVersionUID = -7465717978198819076L;
    private HashMap<Integer, Student> map = new HashMap<>();

    public IntColorMap() {
        map.put(0, Student.YELLOW);
        map.put(1, Student.BLUE);
        map.put(2, Student.GREEN);
        map.put(3, Student.PINK);
        map.put(4, Student.RED);
    }

    public HashMap<Integer, Student> getMap(){
        return map;
    }
}
