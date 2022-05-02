package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void fillBag() {
        GameBoard board = new GameBoard(2);
        board.prepareGame();

        assertEquals(114, board.getBag().size());
    }

    @Test
    void extractStudents() {
        GameBoard board = new GameBoard(2);
        board.prepareGame();

        int oldBagSize;

        oldBagSize = (board.getBag().size());

        board.extractStudents(5);

        assertEquals( oldBagSize-5, board.getBag().size());
    }

    @Test
    void moveMotherNature() {
        GameBoard board = new GameBoard(2);
        board.prepareGame();
        board.setMotherNature(0);
        int steps = (int)(Math.random()*12);
        board.moveMotherNature(steps);

        assertEquals(steps, board.getMotherNature());
    }

    @Test
    void fillIslands() {
        Game game = new Game(2, false);
        game.addPlayer("FirstPlayer", TowerColor.GRAY);
        game.addPlayer("SecondPlayer", TowerColor.BLACK);

        game.getBoard().prepareGame();
        int motherNature = game.getBoard().getMotherNature();

        for(int i = 0; i < 12; i++) {
            if (i != motherNature && i != game.getBoard().getOppositeMotherNature()) {
                assertFalse(game.getBoard().getIslands().get(i).getStudents().isEmpty());
                assertEquals(1, game.getBoard().getIslands().get(i).getStudents().size());
            }
            if(i == motherNature || i == game.getBoard().getOppositeMotherNature()){
                assertTrue(game.getBoard().getIslands().get(i).getStudents().isEmpty());
            }
        }
    }


    @Test
    void prepareGame() {
        Game game = new Game(2, false);
        game.addPlayer("FirstPlayer", TowerColor.WHITE);
        Player player1 = game.getPlayers().get(0);

        game.getBoard().prepareGame();

        assertEquals(114, game.getBoard().getBag().size());
    }
}