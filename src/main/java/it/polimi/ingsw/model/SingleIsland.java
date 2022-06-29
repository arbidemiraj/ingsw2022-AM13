package it.polimi.ingsw.model;

/**
 * Single island state that implements the island state interface
 */
public class SingleIsland implements IslandState{
    @Override
    public int getNumIslands() {
        return 1;
    }
}
