package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class ReducedIsland implements Serializable {
    @Serial
    private static final long serialVersionUID = -4207476138481288015L;
    private int[] numStudents;
    private String owner;
    private ColorIntMap studentColorMap = new ColorIntMap();
    private HashMap<Student, Integer> studentColor = studentColorMap.getMap();
    private boolean isMotherNature;

    public ReducedIsland(int numStudents[], String owner) {
        this.numStudents = numStudents;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void addStudent(Student color){
        numStudents[studentColor.get(color)]++;
    }

    public void removeStudent(Student color){
        numStudents[studentColor.get(color)]--;
    }

    public void setMotherNature(boolean motherNature) {
        isMotherNature = motherNature;
    }

    public boolean isMotherNature() {
        return isMotherNature;
    }

    public int[] getNumStudents() {
        return numStudents;
    }
}
