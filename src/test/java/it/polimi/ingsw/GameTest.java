package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void activateCharacter() throws NotEnoughCoinException {
        Game game = new Game(2);
        Character character = new Character(game, 1);
        Player player = new Player(TowerColor.GRAY, 8);
        game.activateCharacter(character, player);

        assertEquals(true, game.getCharacters()[0].isActivated());
    }


    @Test
    void addMotherNatureMoves() {
        Game game = new Game(2);
        int prevMotherNat = game.getTable().getMotherNature();
        game.addMotherNatureMoves();

        assertEquals(prevMotherNat+1, game.getTable().getMotherNature());
    }

    @Test
    void influence() {
    }

    @Test
    void nextPlayer() {
    }

    @Test
    void professorCheck() {
    }

    @Test
    void testProfessorCheck() {
    }

    @Test
    void conquering() {
    }
}