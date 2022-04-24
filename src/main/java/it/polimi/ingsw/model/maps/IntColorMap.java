package it.polimi.ingsw.model.maps;

import it.polimi.ingsw.model.enumerations.Student;

import java.util.HashMap;

public class IntColorMap {
    private HashMap<Integer, Student> map = new HashMap<Integer, Student>();

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
