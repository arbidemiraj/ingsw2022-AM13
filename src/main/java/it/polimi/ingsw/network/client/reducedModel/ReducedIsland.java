package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.view.cli.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** This class is useful for showing client-side island updates **/
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
    private int numIsland;

    /**
     * Default constructor
     * @param students      list of the students
     * @param id            character id
     * @param isNoEntryTile      true if that island cannot be used
     */
    public ReducedIsland(List<Student> students, String owner, int id, boolean isNoEntryTile) {
        this.students = students;
        this.owner = owner;
        this.id = id;
        this.isNoEntryTile = isNoEntryTile;
        numIsland = 1;
        numStudents = new int[]{0, 0, 0, 0, 0};

        setNumStudents();
    }
    /** This method sets the number of the students **/
    public void setNumStudents(){
        ColorIntMap studentColorMap = new ColorIntMap();
        HashMap<Student, Integer> studentColor = studentColorMap.getMap();

        for (Student student : students) {
            numStudents[studentColor.get(student)]++;
        }
    }

    public int[] getNumStudents() {
        return numStudents;
    }

    public String getOwner() {
        return owner;
    }

    /** This method adds the student **/
    public void addStudent(Student color){
       students.add(color);
       numStudents[studentColor.get(color)]++;
    }

    /**This method removes the student **/
    public void removeStudent(Student color){
        students.remove(color);
        numStudents[studentColor.get(color)]--;
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

    /** This method prints the island **/
    public String printIsland(){
        String print = "";

        print += "Island " + id +
                "\n ------- \n";

        if(isMotherNature){
            print += "   [MN]    \n";
        }

        if(isNoEntryTile){
            print += " NO ENTRY TILE \n";
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

    public void setNumIsland(int numIsland){
        this.numIsland = numIsland;
    }

    public void addIsland() {
        this.numIsland++;
    }

    /** This method merges the students **/
    public void mergeStudents(int[] numStudents) {
        for(int i = 0; i < 5; i++){
            this.numStudents[i] += numStudents[i];
        }
    }

    public int getNumIslands() {
        return numIsland;
    }

    public boolean isNoEntryTile() {
        return isNoEntryTile;
    }

    public void setOwner(String islandOwner) {
        this.owner = islandOwner;
    }

    public void setNoEntryTile(boolean isNoEntryTile) {
        this.isNoEntryTile = isNoEntryTile;
    }
}
