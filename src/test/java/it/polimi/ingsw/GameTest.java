package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

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
        Game game = new Game(2);

        game.setCurrentPlayer(0);

        game.nextPlayer();

        assertEquals(game.getCurrentPlayer(), 1);
    }

    @Test
    void professorCheck() {
        Game game = new Game(2);
        Player player1;
        Student s1 = Student.BLUE, s2 = Student.BLUE, s3 = Student.BLUE;
        ArrayList<Student> students = new ArrayList<>();

        students.add(s1);
        students.add(s2);
        students.add(s3);

        player1 = game.getTable().getPlayers()[0];

        player1.getPlayerBoard().fillDinnerRoom(students);

        game.professorCheck(Student.BLUE);

        assertEquals(game.getTable().getProfessors()[1].getOwner(), player1);
    }

    @Test
    void conquering() {
        Player player1;
        Island island;
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

    @Test
    void mergeCheck() {
        Game game = new Game(2);
        Player player1;
        int motherNature;

        player1 = game.getTable().getPlayers()[0];

        game.getTable().setMotherNature(4);
        motherNature = game.getTable().getMotherNature();

        game.getTable().getIslands().get(motherNature).setOwner(player1);
        game.getTable().getIslands().get(motherNature-1).setOwner(player1);
        game.getTable().getIslands().get(motherNature+1).setOwner(player1);

        game.mergeCheck();

        assertEquals(game.getTable().getIslands().get(motherNature-1).getIslandState().getNumIslands(), 3);
    }
    @Test
    void characterCreation() {
        Game game = new Game(2, true);

        Character character = game.getCharacters()[0];

    }
}