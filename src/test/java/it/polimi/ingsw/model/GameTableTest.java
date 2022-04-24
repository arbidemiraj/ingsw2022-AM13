package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    @Test
    void fillBag() {
        GameTable table = new GameTable(2);
        table.prepareGame();

        assertEquals(118, table.getBag().size());
    }

    @Test
    void extractStudents() {
        GameTable table = new GameTable(2);
        table.prepareGame();

        int oldBagSize;

        oldBagSize = (table.getBag().size());

        table.extractStudents(5);

        assertEquals( oldBagSize-5, table.getBag().size());
    }

    @Test
    void moveMotherNature() {
        GameTable table = new GameTable(2);
        table.prepareGame();
        table.setMotherNature(0);
        int steps = (int)(Math.random()*12);
        table.moveMotherNature(steps);

        assertEquals(steps, table.getMotherNature());
    }

    @Test
    void fillIslands() {
        Game game = new Game(2);
        game.getTable().addPlayer("FirstPlayer");
        game.getTable().addPlayer("SecondPlayer");

        game.getTable().prepareGame();
        int motherNature = game.getTable().getMotherNature();

        for(int i = 0; i < 12; i++) {
            if (i != motherNature && i != game.getTable().getOppositeMotherNature()) {
                assertFalse(game.getTable().getIslands().get(i).getStudents().isEmpty());
                assertEquals(1, game.getTable().getIslands().get(i).getStudents().size());
            }
            if(i == motherNature || i == game.getTable().getOppositeMotherNature()){
                assertTrue(game.getTable().getIslands().get(i).getStudents().isEmpty());
            }
        }
    }

    @Test
    void moveStudentsFromCloud() {
        Game game = new Game(2);
        game.getTable().addPlayer("FirstPlayer");
        game.getTable().addPlayer("SecondPlayer");
        game.getTable().prepareGame();
        Cloud cloud;
        Player currentPlayer = game.getTable().getPlayers().get(game.getCurrentPlayer());


        cloud = game.getTable().getClouds()[0];

        ArrayList<Student> students = new ArrayList<>();

        students.addAll(cloud.getStudents());

        currentPlayer.getPlayerBoard().getEntrance().clear();

        game.getTable().moveStudentsFromCloud(0, game.getCurrentPlayer());

        assertEquals(currentPlayer.getPlayerBoard().getEntrance(), students);
        assertTrue(cloud.getStudents().isEmpty());
    }
    @Test
    void getPlayersByNickname() {

    }

    @Test
    void addPlayer() {
        GameTable gameTable = new GameTable(2);
        gameTable.addPlayer("FirstPlayer");
        Player player = gameTable.getPlayers().get(0);

        assertEquals(1, gameTable.getPlayers().size());
        assertEquals("FirstPlayer", player.getNickname());
    }

    @Test
    void prepareGame() {
        Game game = new Game(2);
    }
}