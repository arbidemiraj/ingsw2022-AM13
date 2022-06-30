package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {
    Game testGame;
    GameController testGameController;
    TurnController testTurnController;

    void prepareTestGame(boolean expertMode){
        testGame = new Game(2, expertMode);
        testGame.addPlayer("FirstPlayer");
        testGame.addPlayer("SecondPlayer");

        testGame.getBoard().prepareGame();

        testGameController = new GameController(testGame, null);
        testTurnController = testGameController.getTurnController();

        testTurnController.firstPlayer();

        testTurnController.firstUsernameQueue();
    }

    @Test
    void firstPlayer() {
        prepareTestGame(false);

        testTurnController.firstPlayer();

        assertTrue(testTurnController.getCurrentPlayer() != null);
        assertTrue(testGame.getPlayers().contains(testGame.getPlayerByUsername(testTurnController.getCurrentPlayerUsername())));
    }

    @Test
    void nextPlayer() {
        prepareTestGame(false);

        String player1 = testGameController.getTurnController().getCurrentPlayerUsername();
        String player2 = testGameController.getTurnController().getUsernameQueue().get(1);


        assertTrue(testTurnController.nextPlayer());
        assertEquals(player2, testTurnController.getCurrentPlayerUsername());

        assertFalse(testTurnController.nextPlayer());
    }
}