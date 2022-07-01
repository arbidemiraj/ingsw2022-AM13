package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Effect7;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
import it.polimi.ingsw.network.message.clientmsg.ChooseCloudMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    Game testGame;
    GameController testGameController;
    TurnController testTurnController;
    
    void prepareTestGame(boolean expertMode){
        testGame = new Game(2, expertMode);
        testGame.addPlayer("FirstPlayer");
        testGame.addPlayer("SecondPlayer");

        testGame.getBoard().prepareGame();

        testGameController = new GameController(testGame, null);
        testTurnController = new TurnController(testGameController, null);
        testGameController.setTurnController(testTurnController);
    }

    @Test
    void moveStudent() {
        prepareTestGame(false);
        Island island = testGame.getBoard().getIslands().get(5);
        PlayerBoard playerBoard = testGame.getPlayers().get(0).getPlayerBoard();
        Student student = Student.BLUE;

        island.getStudents().clear();
        island.addStudent(student);

        try {
            testGameController.moveStudent(island, Student.BLUE, playerBoard, false);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertTrue(playerBoard.getEntrance().contains(Student.BLUE));
    }



    @Test
    void moveStudentsFromCloud() {
        prepareTestGame(false);
        Cloud cloud;

        testTurnController.fillUsernameQueue();
        Player currentPlayer = testTurnController.getCurrentPlayer();

        cloud = testGame.getBoard().getClouds()[0];

        ArrayList<Student> students = new ArrayList<>();

        students.addAll(cloud.getStudents());

        currentPlayer.getPlayerBoard().getEntrance().clear();

        ChooseCloudMessage cloudMessage = new ChooseCloudMessage(testTurnController.getCurrentPlayerUsername(), 0);
        testGameController.moveStudentsFromCloud(cloudMessage.getCloudId());

        assertEquals(currentPlayer.getPlayerBoard().getEntrance(), students);
        assertTrue(cloud.getStudents().isEmpty());
    }



    @Test
    void playCard(){
        prepareTestGame(false);

        testTurnController.calcCurrentPlayer("FirstPlayer");

        Player player = testTurnController.getCurrentPlayer();

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            testGameController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        assertEquals(9, player.getDeck().size());
        assertEquals(assistantCard, testTurnController.getTurnCardsPlayed().get(0));

    }

    @Test
    void activateCharacter(){
        prepareTestGame(true);

        Character character = testGame.getCharacters()[0];
        int cost = character.getCost();
        Player player = testGame.getPlayers().get(0);

        player.setNumCoins(cost);

        testTurnController.calcCurrentPlayer(player.getUsername());

        testGameController.activateCharacter(character.getEffectId());



        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(testGame.getActivatedCharacters().contains(character.getEffectId()));
    }

    @Test
    void activateIslandCharacter() {
        prepareTestGame(true);

        Island chosenIsland = testGame.getBoard().getIslands().get(3);

        Character character = new Character(testGame, 3, 3);
        testGame.getCharacters()[0] = character;

        Player player = testGame.getPlayers().get(0);
        testTurnController.calcCurrentPlayer(player.getUsername());
        player.setNumCoins(character.getCost());



        testGameController.activateIslandCharacter(3, chosenIsland);



        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(testGame.getActivatedCharacters().contains(3));
    }

    @Test
    void activateStudentCharacter() {
        prepareTestGame(true);
        Student chosenStudent = Student.YELLOW;

        Character character = new Character(testGame, 1, 1);
        testGame.getCharacters()[0] = character;

        Player player = testGame.getPlayers().get(0);
        player.setNumCoins(character.getCost());

        testTurnController.calcCurrentPlayer(player.getUsername());


        testGameController.activateStudentCharacter(1, chosenStudent);


        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(testGame.getActivatedCharacters().contains(1));
    }



    @Test
    void moveMotherNature() {
        prepareTestGame(false);

        Player player = testGame.getPlayers().get(0);

        testTurnController.calcCurrentPlayer(player.getUsername());

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            testGameController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        int prevMotherNature = testGame.getBoard().getMotherNature();

        try {
            testGameController.moveMotherNature(assistantCard.getMaxMotherNatureMoves()-1);
        } catch (InvalidMotherNatureMovesException e) {
            e.printStackTrace();
        }
        int moves = assistantCard.getMaxMotherNatureMoves()-1;

        assertEquals(prevMotherNature+moves, testGame.getBoard().getMotherNature());

    }

    @Test
    void moveMotherNatureFail() {

        prepareTestGame(false);

        Player player = testGame.getPlayers().get(0);

        AssistantCard assistantCard = player.getDeck().get(0);

        testTurnController.calcCurrentPlayer(player.getUsername());

        try {
            testGameController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        int prevMotherNature = testGame.getBoard().getMotherNature();

        try {
            testGameController.moveMotherNature(assistantCard.getMaxMotherNatureMoves()+1);
        } catch (InvalidMotherNatureMovesException e) {
        }

        assertEquals(prevMotherNature, testGame.getBoard().getMotherNature());


    }

    @Test
    void addPlayer() {
        testGame = new Game(2, true);
        testGameController = new GameController(testGame, null);

        testGameController.addPlayer("First");

        assertEquals("First", testGameController.getGame().getPlayers().get(0).getUsername());
    }

    @Test
    void endGame() {
        prepareTestGame(false);
        Player player = testGame.getPlayers().get(0);

        for(int i = 0; i < 4; i++){
            testGame.getBoard().getIslands().get(i).setOwner(player);
        }

        assertEquals(player.getUsername(), testGameController.endGame());
    }

    @Test
    void activateEffect7() {
        prepareTestGame(true);
        Player player = testGame.getPlayers().get(0);
        player.getPlayerBoard().addStudent(Student.BLUE);

        testGame.getCharacters()[0] = new Character(testGame, 7, 1);

        ArrayList<Student> students = new ArrayList<>();

        students.add(((Effect7)testGame.getCharacters()[0].getEffect()).getStudents().get(0));

        students.add(Student.BLUE);

        testGameController.activateEffect7(students, player.getUsername());

        int pos = ((Effect7)testGame.getCharacters()[0].getEffect()).getStudents().size();
        int pos2 = player.getPlayerBoard().getEntrance().size();



        assertEquals(students.get(1), ((Effect7)testGame.getCharacters()[0].getEffect()).getStudents().get(pos - 1));
        assertEquals(player.getPlayerBoard().getEntrance().get(pos2 - 1), students.get(0));

    }

    @Test
    void activateEffect10() {
        prepareTestGame(true);
        Player player = testGame.getPlayers().get(0);
        player.getPlayerBoard().addStudent(Student.YELLOW);
        player.getPlayerBoard().getDinnerRoom()[1].addStudent(Student.BLUE);

        testGame.getCharacters()[0] = new Character(testGame, 10, 1);

        ArrayList<Student> students = new ArrayList<>();

        students.add(Student.YELLOW);

        students.add(Student.BLUE);

        try {
            testGameController.activateEffect10(students, player.getUsername());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(1, player.getPlayerBoard().getDinnerRoom()[0].getNumStudents());
        assertEquals(Student.BLUE, player.getPlayerBoard().getEntrance().get(0));
    }
}