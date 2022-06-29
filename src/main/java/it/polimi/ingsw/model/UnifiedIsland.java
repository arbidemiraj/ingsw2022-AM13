package it.polimi.ingsw.model;

/**
 * Unified island state class
 */
public class UnifiedIsland implements IslandState{
    private int numIslands = 0;

    /**
     * Default constructor
     * @param numIslands the number of islands that make the island
     */
    public UnifiedIsland(int numIslands) {
        this.numIslands = numIslands;
    }

    /**
     * adds an island
     */
    public void addIsland() {
        this.numIslands++;
    }

    /**
     * returns the number of islands
     * @return the number of islands
     */
    @Override
    public int getNumIslands() {
        return numIslands;
    }
}
