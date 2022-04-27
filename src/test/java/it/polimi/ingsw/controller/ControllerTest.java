package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
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

        controller.moveStudent(island, Student.BLUE, playerBoard);

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
        assertTrue(game.getBoard().getPlayers().contains(game.getBoard().getPlayerByNickname(controller.getCurrentPlayerNickname())));
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

    @Test
    void activateCharacter() throws NotEnoughCoinException {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer");
        game.getBoard().addPlayer("SecondPlayer");
        game.getBoard().prepareGame();

        Controller controller = new Controller(game);

        it.polimi.ingsw.model.Character character = game.getCharacters()[0];
        int cost = character.getCost();
        Player player = game.getBoard().getPlayers().get(0);

        player.setNumCoins(cost);

        controller.setCurrentPlayer(player.getNickname());

        controller.activateCharacter(character.getEffectId());

        assertEquals(character.getOwner(), player);

    }

    @Test
    void activateCharacterFail() throws NotEnoughCoinException {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer");
        game.getBoard().addPlayer("SecondPlayer");

        game.getBoard().prepareGame();
        Controller controller = new Controller(game);

        Character character = game.getCharacters()[0];
        Player player = game.getBoard().getPlayers().get(0);
        player.setNumCoins(0);

        controller.setCurrentPlayer(player.getNickname());

        try {
            controller.activateCharacter(character.getEffectId());
        }
        catch(NotEnoughCoinException e) {
        }


        assertNull(character.getOwner());

    }
}