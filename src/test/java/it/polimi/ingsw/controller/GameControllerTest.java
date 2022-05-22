package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
import it.polimi.ingsw.network.message.clientmsg.ChooseCloudMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    Game testGame;
    GameController testGameController;
    
    void prepareTestGame(boolean expertMode){
        testGame = new Game(2, expertMode);
        testGame.addPlayer("FirstPlayer");
        testGame.addPlayer("SecondPlayer");

        testGame.getBoard().prepareGame();

        testGameController = new GameController(testGame, null);
    }

    @Test
    void endingConditionCheck1() {
        prepareTestGame(false);

        testGame.getBoard().getBag().clear();

        assertTrue(testGameController.endingConditionCheck());
    }

    @Test
    void endingConditionCheck2() {
        prepareTestGame(false);

        testGame.getPlayers().get(0).setNumTowers(0);

        assertTrue(testGameController.endingConditionCheck());
    }

    @Test
    void moveStudent() {
        prepareTestGame(false);
        Island island = testGame.getBoard().getIslands().get(5);
        PlayerBoard playerBoard = testGame.getPlayers().get(0).getPlayerBoard();
        Student student = Student.BLUE;

        island.getStudents().clear();
        island.addStudent(student);

        testGameController.moveStudent(island, Student.BLUE, playerBoard, false);

        assertTrue(playerBoard.getEntrance().contains(Student.BLUE));
    }

    @Test
    void firstPlayer() {
        prepareTestGame(false);

        testGameController.firstPlayer();

        assertTrue(testGameController.currentPlayer() != null);
        assertTrue(testGame.getPlayers().contains(testGame.getPlayerByUsername(testGameController.getCurrentPlayerUsername())));
    }

    @Test
    void moveStudentsFromCloud() {
        prepareTestGame(false);
        Cloud cloud;

        testGameController.setCurrentPlayer();
        Player currentPlayer = testGameController.getCurrentPlayer();

        cloud = testGame.getBoard().getClouds()[0];

        ArrayList<Student> students = new ArrayList<>();

        students.addAll(cloud.getStudents());

        currentPlayer.getPlayerBoard().getEntrance().clear();

        ChooseCloudMessage cloudMessage = new ChooseCloudMessage(testGameController.getCurrentPlayerUsername(), 0);
        testGameController.moveStudentsFromCloud(cloudMessage.getCloudId());

        assertEquals(currentPlayer.getPlayerBoard().getEntrance(), students);
        assertTrue(cloud.getStudents().isEmpty());
    }

    @Test
    void nextPlayer() {
        prepareTestGame(false);

        testGameController.setCurrentPlayer("FirstPlayer");

        testGameController.nextPlayer();

        assertEquals("SecondPlayer", testGameController.getCurrentPlayerUsername());

        testGameController.nextPlayer();

        assertEquals("FirstPlayer", testGameController.getCurrentPlayerUsername());
    }

    @Test
    void playCard(){
        prepareTestGame(false);

        testGameController.setCurrentPlayer("FirstPlayer");

        Player player = testGameController.getCurrentPlayer();

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            testGameController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        assertEquals(9, player.getDeck().size());
        assertEquals(assistantCard, testGameController.getTurnCardsPlayed().get(0));
    }

    @Test
    void activateCharacter(){
        prepareTestGame(true);

        Character character = testGame.getCharacters()[0];
        int cost = character.getCost();
        Player player = testGame.getPlayers().get(0);

        player.setNumCoins(cost);

        testGameController.setCurrentPlayer(player.getUsername());

        try{
            testGameController.activateCharacter(character.getEffectId());
        } catch (NotEnoughCoinException e) {
            e.printStackTrace();
        }


        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(testGame.getActivatedCharacters().contains(character.getEffectId()));
    }

    @Test
    void activateCharacterFail(){
        prepareTestGame(true);

        Character character = testGame.getCharacters()[0];
        Player player = testGame.getPlayers().get(0);
        player.setNumCoins(0);

        testGameController.setCurrentPlayer(player.getUsername());

        try {
            testGameController.activateCharacter(character.getEffectId());
        }
        catch(NotEnoughCoinException e) {
        }


        assertNull(character.getOwner());
        assertFalse(character.isActivated());
    }

    @Test
    void activateIslandCharacter() {
        prepareTestGame(true);

        Island chosenIsland = testGame.getBoard().getIslands().get(3);

        Character character = new Character(testGame, 3, 3);
        testGame.getCharacters()[0] = character;

        Player player = testGame.getPlayers().get(0);
        player.setNumCoins(character.getCost());

        testGameController.setCurrentPlayer(player.getUsername());

        try {
            testGameController.activateIslandCharacter(3, chosenIsland);
        }
        catch(NotEnoughCoinException e) {
        }


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

        testGameController.setCurrentPlayer(player.getUsername());

        try {
            testGameController.activateStudentCharacter(1, chosenStudent);
        }
        catch(NotEnoughCoinException e) {
        }


        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(testGame.getActivatedCharacters().contains(1));
    }

    @Test
    void setCurrentPlayer() {
        prepareTestGame(false);

        Player player1 = testGame.getPlayers().get(0);
        Player player2 = testGame.getPlayers().get(1);

        AssistantCard assistantCard1 = player1.getDeck().get(1);
        AssistantCard assistantCard2 = player2.getDeck().get(5);

        testGameController.setCurrentPlayer("FirstPlayer");

        try {
            testGameController.playCard(assistantCard1);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        nextPlayer();

        try {
            testGameController.playCard(assistantCard2);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        testGameController.setCurrentPlayer();
        assertEquals("FirstPlayer", testGameController.getCurrentPlayerUsername());
    }

    @Test
    void moveMotherNature() {
        prepareTestGame(false);

        Player player = testGame.getPlayers().get(0);

        testGameController.setCurrentPlayer(player.getUsername());

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

        testGameController.setCurrentPlayer(player.getUsername());

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
}