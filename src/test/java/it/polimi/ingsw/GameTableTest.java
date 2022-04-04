package it.polimi.ingsw;

import jdk.internal.icu.text.UnicodeSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {


    @Test
    void testExtractStudents() {
        GameTable table = new GameTable(2);
        int oldBagSize;

        table.fillBag();
        oldBagSize = table.getBag().size();

        table.extractStudents(5);

        assertEquals(table.getBag().size(), oldBagSize-5);

    }
}