package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    /*@Test
    void activateCharacter() throws NotEnoughCoinException {
        Game game = new Game(2, true);
        Character character = game.getCharacters()[0];
        Player player = game.getTable().getPlayers()[0];
        player.addCoin();

        game.activateCharacter(character, player);

        assertTrue(game.getCharacters()[0].isActivated());
    }*/

    @Test
    void influence() {
        Player player1, player2;
        Game game = new Game(2);
        int motherNature;

        player1 = game.getTable().getPlayers()[0];
        player2 = game.getTable().getPlayers()[1];
        motherNature = game.getTable().getMotherNature();

        game.getTable().getProfessors()[0].setOwner(player1);

        for(int i = 0; i < 5; i++) game.getTable().getIslands().get(motherNature).addStudent(Student.YELLOW);

        game.influence();

        assertTrue(player1.getInfluenceValue() > player2.getInfluenceValue());
    }

    @Test
    void addMotherNatureMoves() {
        Game game = new Game(2);
        int prevMotherNat = game.getTable().getMotherNature();
        game.addMotherNatureMoves();

        assertEquals(prevMotherNat+2, game.getTable().getMotherNature());
    }

    @Test
    void nextPlayer() {
        Player player1, player2;
        Game game = new Game(2);

        player1 = game.getTable().getPlayers()[0];
        player2 = game.getTable().getPlayers()[1];
        game.setCurrentPlayer(0);

        game.nextPlayer();

        assertEquals(game.getCurrentPlayer(), 1);
    }

    @Test
    void professorCheck() {
    }

    @Test
    void testProfessorCheck() {
    }

    @Test
    void conquering() {
        Player player1;
        Island island = new Island();
        Game game = new Game(2);
        int motherNature;

        player1 = game.getTable().getPlayers()[0];

        motherNature = game.getTable().getMotherNature();

        island = game.getTable().getIslands().get(motherNature);

        for(int i = 0; i < 5; i++) island.addStudent(Student.YELLOW);

        game.getTable().getProfessors()[0].setOwner(player1);

        game.conquering();

        assertTrue(player1 == island.getOwner());
    }
}