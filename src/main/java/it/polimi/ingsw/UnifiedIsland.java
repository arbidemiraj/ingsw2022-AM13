package it.polimi.ingsw;

public class UnifiedIsland implements IslandState{
    private int numIslands = 0;

    public UnifiedIsland() {
        numIslands++;
    }

    public int getNumIslands() {
        return numIslands;
    }

    public void addIsland() {
        this.numIslands++;
    }
}
