package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void applyEffect() {
        Game game = new Game(2, true);

        Character character = game.getCharacters()[0];

        character.applyEffect();

        assertTrue(game.getCharacters()[0].isActivated());
    }
}