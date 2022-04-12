package it.polimi.ingsw;

public class UnifiedIsland implements IslandState{
    private int numIslands = 0;

    public UnifiedIsland(int numIslands) {
        this.numIslands = numIslands;
    }

    public void addIsland() {
        this.numIslands++;
    }

    @Override
    public int getNumIslands() {
        return numIslands;
    }
}
