package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.view.cli.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReducedIsland implements Serializable {
    @Serial
    private static final long serialVersionUID = -4207476138481288015L;
    private List<Student> students;
    private int id;
    private String owner;
    private ColorIntMap studentColorMap = new ColorIntMap();
    private HashMap<Student, Integer> studentColor = studentColorMap.getMap();
    private boolean isMotherNature;
    private boolean isNoEntryTile;
    private int[] numStudents;

    public ReducedIsland(List<Student> students, String owner, int id, boolean isNoEntryTile) {
        this.students = students;
        this.owner = owner;
        this.id = id;
        this.isNoEntryTile = isNoEntryTile;

        numStudents = new int[]{0, 0, 0, 0, 0};

        setNumStudents();
    }

    public void setNumStudents(){
        ColorIntMap studentColorMap = new ColorIntMap();
        HashMap<Student, Integer> studentColor = studentColorMap.getMap();

        for (Student student : students) {
            numStudents[studentColor.get(student)]++;
        }
    }

    public String getOwner() {
        return owner;
    }

    public void addStudent(Student color){
       students.add(color);
    }

    public void removeStudent(Student color){
        students.remove(color);
    }

    public void setMotherNature(boolean motherNature) {
        isMotherNature = motherNature;
    }

    public boolean isMotherNature() {
        return isMotherNature;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String printIsland(){
        String print = "";

        print += "Island " + id +
                "\n ------- \n";

        if(isMotherNature){
            print += "   [MN]    \n";
        }

        if(owner != null){
            print += "  [ " + owner + " ]   \n";
        }
                print += Color.ANSI_YELLOW + " Y: " + numStudents[0] +
                        Color.ANSI_BLUE + "\n B: " + numStudents[1] +
                        Color.ANSI_GREEN +"\n G: " + numStudents[2] +
                        Color.ANSI_PINK +"\n P: " + numStudents[3] +
                        Color.ANSI_RED +"\n R: " + numStudents[4] +
                        Color.RESET +
                        "\n ------- \n";

        return print;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
