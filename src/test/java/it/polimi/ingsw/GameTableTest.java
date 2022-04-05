package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    @Test
    void fillBag() {
        GameTable table = new GameTable(2);

        assertEquals(120, table.getBag().size());
    }

    @Test
    void extractStudents() {
        GameTable table = new GameTable(2);
        int oldBagSize;

        oldBagSize = (table.getBag().size());

        table.extractStudents(5);

        assertEquals( oldBagSize-5, table.getBag().size());
    }

    @Test
    void moveMotherNature() {
        GameTable table = new GameTable(2);
        table.setMotherNature(0);
        int steps = (int)(Math.random()*12);
        table.moveMotherNature(steps);

        assertEquals(steps, table.getMotherNature());
    }
}