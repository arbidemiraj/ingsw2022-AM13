package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    @Test
    void fillBag() {
        GameTable table = new GameTable(2);

        assertEquals(114, table.getBag().size());
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

    @Test
    void fillIslands() {
        Game game = new Game(2);
        int motherNature = game.getTable().getMotherNature();

        for(int i = 0; i < 12; i++) {
            if (i != motherNature && i != game.getTable().getOppositeMotherNature()) {
                assertFalse(game.getTable().getIslands().get(i).getStudents().isEmpty());
            }
            if(i == motherNature || i == game.getTable().getOppositeMotherNature()){
                assertTrue(game.getTable().getIslands().get(i).getStudents().isEmpty());
            }
        }
    }

    @Test
    void moveStudentsFromCloud() {
        Game game = new Game(2);
        Cloud cloud = new Cloud(2);
        Player currentPlayer = game.getTable().getPlayers()[game.getCurrentPlayer()];


        cloud = game.getTable().getClouds()[0];

        ArrayList<Student> students = new ArrayList<>();

        students.addAll(cloud.getStudents());

        currentPlayer.getPlayerBoard().getEntrance().clear();

        game.getTable().moveStudentsFromCloud(0, game.getCurrentPlayer());

        assertEquals(currentPlayer.getPlayerBoard().getEntrance(), students);
        assertTrue(cloud.getStudents().isEmpty());
    }
}