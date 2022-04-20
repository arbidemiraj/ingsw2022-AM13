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
        Student s1 = Student.BLUE;
        ArrayList<Student> students = new ArrayList<>();

        students.add(s1);
        students.add(s1);
        students.add(s1);

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
    void setupExpertMode() {
        Game game = new Game(2, true);

        assertEquals(3, game.getCharacters().length);
        for(Character character : game.getCharacters()){
            assertTrue(character != null);
        }
    }

    @Test
    void planningPhase() {
    }

    @Test
    void actionPhase() {
    }

    @Test
    void chooseEffect() {
    }

    @Test
    void endingConditionCheck1() {
        Game game = new Game(2);

        game.getTable().getBag().clear();

        assertTrue(game.endingConditionCheck());
    }
    @Test
    void endingConditionCheck2() {
        Game game = new Game(2);

        game.getTable().getPlayers()[0].setNumTowers(0);

        assertTrue(game.endingConditionCheck());
    }

    @Test
    void unifyIslands() {
        Game game = new Game(2);
        Student student1, student2, student3;
        Island island1 = new Island();
        Island island2 = new Island();

        student1 = Student.YELLOW;
        student2 = Student.YELLOW;
        student3 = Student.BLUE;

        island1.addStudent(student1);
        island2.addStudent(student2);
        island2.addStudent(student3);

        game.unifyIslands(island1, island2);

        assertEquals(2, island1.getIslandState().getNumIslands());
        assertEquals(3, island1.getStudents().size());
    }

    @Test
    void influenceIsland() {
        Game game = new Game(2, true);
        Player player;
        Island island;

        int motherNature = game.getTable().getMotherNature();

        player = game.getTable().getPlayers()[0];
        game.getTable().getProfessors()[0].setOwner(player);

        island = game.getTable().getIslands().get(5);

        island.addStudent(Student.YELLOW);
        game.influence(island);

        assertEquals(island.getNumStudents()[0], player.getInfluenceValue());
    }

    @Test
    void expertProfessorCheck() {
        Game game = new Game(2, true);
        Player player1, player2;
        player1 = game.getTable().getPlayers()[0];
        player2 = game.getTable().getPlayers()[1];

        Character character = new Character(game, 2);
        character.setOwner(player1);

        Student s1 = Student.BLUE;
        ArrayList<Student> students1 = new ArrayList<>();
        ArrayList<Student> students2 = new ArrayList<>();

        students1.add(s1);
        students1.add(s1);
        students2.add(s1);
        students2.add(s1);

        player1.getPlayerBoard().fillDinnerRoom(students1);
        player2.getPlayerBoard().fillDinnerRoom(students2);

        game.professorCheck(character, Student.BLUE);

        assertEquals(player1, game.getTable().getProfessors()[1].getOwner());
    }

    @Test
    void expertConquering() {
        Game game = new Game(2, true);
        Character character = new Character(game, 6);
        Player player1, player2;

        Island island = game.getTable().getMotherNatureIsland();

        player1 = game.getTable().getPlayers()[0];
        player2 = game.getTable().getPlayers()[1];

        character.setOwner(player1);
        game.getTable().getProfessors()[0].setOwner(player1);
        game.getTable().getProfessors()[1].setOwner(player2);

        island.addStudent(Student.YELLOW);
        island.setOwner(player2);

        game.conquering(character);

        assertEquals(player1, island.getOwner());
    }

    @Test
    void activateCharacter() throws NotEnoughCoinException {
        Game game = new Game(2, true);
        Character character = game.getCharacters()[0];
        int cost = character.getCost();
        Player player = game.getTable().getPlayers()[0];

        player.setNumCoins(cost);

        game.activateCharacter(character, player);

        assertEquals(character.getOwner(), player);

    }

    @Test
    void activateCharacterFail() throws NotEnoughCoinException {
        Game game = new Game(2, true);
        Character character = game.getCharacters()[0];
        Player player = game.getTable().getPlayers()[0];
        player.setNumCoins(0);

        try {
            game.activateCharacter(character, player);
        }
        catch(NotEnoughCoinException e) {
        }


        assertNull(character.getOwner());

    }

    @Test
    void expertSetInfluencePlayer() {
    }

    @Test
    void twoPlayersGame(){
        Game game = new Game(2);

        assertEquals(2, game.getTable().getPlayers().length);

        for(Player player : game.getTable().getPlayers()){
            assertEquals(8, player.getNumTowers());
            assertEquals(7, player.getPlayerBoard().getEntrance().size());
        }

        for(Cloud cloud : game.getTable().getClouds()){
            assertEquals(3, cloud.getStudents().size());
        }
    }

    @Test
    void threePlayersGame(){
        Game game = new Game(3);

        assertEquals(3, game.getTable().getPlayers().length);

        for(Player player : game.getTable().getPlayers()){
            assertEquals(6, player.getNumTowers());
            assertEquals(9, player.getPlayerBoard().getEntrance().size());
        }

        for(Cloud cloud : game.getTable().getClouds()){
            assertEquals(4, cloud.getStudents().size());
        }

    }
}