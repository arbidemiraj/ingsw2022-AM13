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
    private ArrayList<ReducedIsland> islands;
    private ArrayList<ReducedPlayerBoard> playerBoards;

    public ReducedBoard(GameBoard gameBoard) {
        islands = new ArrayList<>();
        int numStudents[];
        String owner;
        for(int i = 0; i < 12; i ++){
            numStudents = gameBoard.getIslands().get(i).getNumStudents();
            if(gameBoard.getIslands().get(i).getOwner()!=null)
            {
                owner = gameBoard.getIslands().get(i).getOwner().getUsername();
            }
            else owner = "No owner";

            islands.add(new ReducedIsland(numStudents, owner));
        }

        this.motherNature = gameBoard.getMotherNature();
    }

    public void setPlayerBoards(ArrayList<ReducedPlayerBoard> playerBoards) {
        this.playerBoards = playerBoards;
    }

    public void addPlayerBoard(ReducedPlayerBoard playerBoard){
        playerBoards.add(playerBoard);
    }

    public int getMotherNature() {
        return motherNature;
    }

    public void setMotherNature(int motherNature) {
        this.motherNature = motherNature;
    }

    public String printIslands() {
        String printedIslands = "";
        int i = 1;

        for(ReducedIsland island : islands){
            printedIslands += "\n Island " + i + "\n" +
                    "  ------- \n |      |" +
                    "\n |      |" +
                    "Y:" + island.getNumStudents()[0] +
                    "  B:" + island.getNumStudents()[1] +
                    "  G:" + island.getNumStudents()[2] +
                    "  P:" + island.getNumStudents()[3] +
                    "  R:" + island.getNumStudents()[4] +
                    "\n |      |" +
                    "\n _______ \n";

            i++;
        }

        return printedIslands;
    }
}
