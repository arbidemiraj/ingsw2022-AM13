package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.view.cli.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class ReducedIsland implements Serializable {
    @Serial
    private static final long serialVersionUID = -4207476138481288015L;
    private int[] numStudents;
    private int id;
    private String owner;
    private ColorIntMap studentColorMap = new ColorIntMap();
    private HashMap<Student, Integer> studentColor = studentColorMap.getMap();
    private boolean isMotherNature;
    private boolean isNoEntryTile;

    public ReducedIsland(int numStudents[], String owner, int id, boolean isNoEntryTile) {
        this.numStudents = numStudents;
        this.owner = owner;
        this.id = id;
        this.isNoEntryTile = isNoEntryTile;
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

    public String printIsland(){
        String print = "";

        print += "Island " + id +
                "\n ------- \n";

        if(isMotherNature){
            print += "   [MN]    \n";
        }

        if(owner != null){
            print += "  [ " + owner + " ]   ";
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
}
