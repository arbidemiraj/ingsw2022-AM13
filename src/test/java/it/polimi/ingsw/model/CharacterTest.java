package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void applyEffect() {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer");
        game.getBoard().addPlayer("SecondPlayer");

        game.getBoard().prepareGame();
        Character character = game.getCharacters()[0];

        character.applyEffect();

        assertTrue(game.getCharacters()[0].isActivated());
    }
}