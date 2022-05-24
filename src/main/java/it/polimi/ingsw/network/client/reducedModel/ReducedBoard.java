package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.PlayerBoard;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ReducedBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = -6983972997025348137L;
    private Cloud[] clouds;
    private int motherNature;
    private int[][] numStudents;
    private String[] owner;
    private ArrayList<ReducedIsland> islands;
    private ReducedPlayerBoard playerBoard;

    public ReducedBoard(int[][] numStudents, Cloud[] clouds, String[] owner, ReducedPlayerBoard reducedPlayerBoard, int motherNature) {
        islands = new ArrayList<>();

        this.clouds = clouds;
        this.numStudents = numStudents;
        this.owner = owner;
        this.playerBoard = reducedPlayerBoard;

        for(int i = 0; i < 12; i ++){
            islands.add(new ReducedIsland(numStudents[i], owner[i], i));
        }

        this.motherNature = motherNature;

        islands.get(motherNature).setMotherNature(true);
    }


    public int getMotherNature() {
        return motherNature;
    }

    public void setMotherNature(int motherNature) {
        this.motherNature = motherNature;
    }

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
}
