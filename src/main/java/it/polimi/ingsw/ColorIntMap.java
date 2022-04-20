package it.polimi.ingsw;

import java.util.HashMap;

public class ColorIntMap {
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
