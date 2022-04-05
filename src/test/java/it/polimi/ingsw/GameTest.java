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

        assertTrue(game.getCharacters()[0].isActivated());
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
        Player player1, player2;
        Island island = new Island();
        Game game = new Game(2);
        int motherNature;

        player1 = game.getTable().getPlayers()[0];
        player2 = game.getTable().getPlayers()[1];

        motherNature = game.getTable().getMotherNature();

        for(int i = 0; i < 2; i++) game.getTable().getIslands().get(motherNature).addStudent(Student.YELLOW);

        game.getTable().getProfessors()[0].setOwner(player1);

        game.influence();

        assertTrue(player1.getInfluenceValue() > player2.getInfluenceValue());
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