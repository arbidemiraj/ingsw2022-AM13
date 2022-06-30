package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/** This class is useful for showing client-side game board updates **/

public class ReducedBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = -6983972997025348137L;
    private Cloud[] clouds;
    private int motherNature;
    private String[] owner;
    private ArrayList<ReducedIsland> islands;
    private ReducedPlayerBoard playerBoard;

    /**
     * Default constructor
     * @param clouds       list of clouds
     * @param reducedPlayerBoard       lighter version of the player board
     * @param motherNature        motherNature position
     * @param islands          list of islands after reduction
     */
    public ReducedBoard(Cloud[] clouds, String[] owner, ReducedPlayerBoard reducedPlayerBoard, int motherNature, ArrayList<ReducedIsland> islands) {
        this.clouds = clouds;
        this.owner = owner;
        this.playerBoard = reducedPlayerBoard;
        this.islands = islands;

        this.motherNature = motherNature;

        islands.get(motherNature).setMotherNature(true);
    }


    public Cloud[] getClouds() {
        return clouds;
    }


    public ArrayList<ReducedIsland> getIslands() {
        return islands;
    }


    public int getMotherNature() {
        return motherNature;
    }

    public void setMotherNature(int motherNature) {
        this.motherNature = motherNature;
    }

    /** This method removes the student from the island **/
    public void removeStudentFromIsland(int id, Student color){
        islands.get(id).addStudent(color);
    }

    /** This method adds the student to the island **/
    public void addStudentToIsland(int id, Student color){
        islands.get(id).addStudent(color);
    }

    /** This method prints the islands **/
    public String printIslands() {
        String printedIslands = "";
        
        for(int i = 0; i < 12; i++){
            printedIslands += islands.get(i).printIsland() + "\n";
        }

        return printedIslands;
    }

    public String printPlayerBoard(){
        return playerBoard.print();
    }

    /** This method prints the clouds **/
    public String printClouds() {
        String print = "";
        int i = 1;

        for(Cloud cloud : clouds){
            print += "Cloud " + i +
                    "\n[ " + cloud.getStudents() + " ]\n ";

            i++;
        }
        return print;
    }

    /** This method moves motherNature **/
    public void moveMotherNature(int steps){
        motherNature += steps;

        if(motherNature >= 12){
            motherNature = motherNature - 12;
        }
    }

    public void setIslands(ArrayList<ReducedIsland> islands) {
        this.islands = islands;
    }

    public ReducedPlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /** This method merge the islands **/
    public void mergeIslands(int island1, int island2) {
        islands.get(island2).mergeStudents(islands.get(island1).getNumStudents());

        islands.get(island2).setNumIsland(islands.get(island2).getNumIslands() + islands.get(island1).getNumIslands());

        islands.remove(island1);
    }

    public void setClouds(Cloud[] clouds) {
        this.clouds = clouds;
    }
}
