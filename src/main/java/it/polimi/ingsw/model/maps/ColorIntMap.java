package it.polimi.ingsw.model.maps;

import it.polimi.ingsw.model.enumerations.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class ColorIntMap implements Serializable {
    @Serial
    private static final long serialVersionUID = -3587825076331083306L;
    private HashMap<Student, Integer> map = new HashMap<>();

    public ColorIntMap() {
        map.put(Student.YELLOW, 0);
        map.put(Student.BLUE, 1);
        map.put(Student.GREEN, 2);
        map.put(Student.PINK, 3);
        map.put(Student.RED, 4);
    }

    public HashMap<Student, Integer> getMap(){
        return map;
    }
}
