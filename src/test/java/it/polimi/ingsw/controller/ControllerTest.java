package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.enumerations.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void endingConditionCheck1() {
        Game game = new Game(2);
        game.getBoard().addPlayer("FirstPlayer");
        game.getBoard().addPlayer("SecondPlayer");

        game.getBoard().prepareGame();

        Controller gameController = new Controller(game);

        game.getBoard().getBag().clear();

        assertTrue(gameController.endingConditionCheck());
    }
    @Test
    void endingConditionCheck2() {
        Game game = new Game(2);
        game.getBoard().addPlayer("FirstPlayer");
        game.getBoard().addPlayer("SecondPlayer");

        game.getBoard().prepareGame();

        Controller gameController = new Controller(game);

        game.getBoard().getPlayers().get(0).setNumTowers(0);

        assertTrue(gameController.endingConditionCheck());
    }

    @Test
    void applyIslandEffect() {
    }

    @Test
    void applyStudentEffect() {
    }

    @Test
    void applyEffect() {
    }

    @Test
    void moveStudent() {
        Game game = new Game(2);
        game.getBoard().prepareGame();
        game.getBoard().addPlayer("FirstPlayer");
        Island island = game.getBoard().getIslands().get(5);
        PlayerBoard playerBoard = game.getBoard().getPlayers().get(0).getPlayerBoard();
        Student student = Student.BLUE;

        island.getStudents().clear();
        island.addStudent(student);

        Controller controller = new Controller(game);

        controller.moveStudent(island, 0, playerBoard);

        assertTrue(playerBoard.getEntrance().contains(Student.BLUE));
    }

    @Test
    void firstPlayer() {
        Game game = new Game(2);
        game.getBoard().prepareGame();
        game.getBoard().addPlayer("FirstPlayer");
        Controller controller = new Controller(game);

        controller.firstPlayer();

        assertTrue(controller.currentPlayer() != null);
    }

    @Test
    void currentPlayer() {
    }

    @Test
    void nextPlayer() {
    }

    @Test
    void playCard() {
    }
}