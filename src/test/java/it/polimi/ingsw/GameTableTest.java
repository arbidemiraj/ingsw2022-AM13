package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    @Test
    void extractStudents() {
        GameTable table = new GameTable(2);
        int oldBagSize;

        table.fillBag();
        oldBagSize = table.getBag().size();

        table.extractStudents(5);

        assertEquals(oldBagSize-5, table.getBag().size());
    }

    @Test
    void fillBag() {
        GameTable table = new GameTable(2);
        table.fillBag();

        assertEquals(120, table.getBag().size());
    }

    @Test
    void moveMotherNature() {
        GameTable table = new GameTable(2);
        table.moveMotherNature(1);

        assertEquals(1, table.getMotherNature());
    }
}