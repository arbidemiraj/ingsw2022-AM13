package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Game testGame;
    Controller testController;
    
    void prepareTestGame(boolean expertMode){
        testGame = new Game(2, expertMode);
        testGame.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        testGame.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);

        testGame.getBoard().prepareGame();

        testController = new Controller(testGame);
    }

    @Test
    void endingConditionCheck1() {
        prepareTestGame(false);

        testGame.getBoard().getBag().clear();

        assertTrue(testController.endingConditionCheck());
    }

    @Test
    void endingConditionCheck2() {
        prepareTestGame(false);

        testGame.getBoard().getPlayers().get(0).setNumTowers(0);

        assertTrue(testController.endingConditionCheck());
    }

    @Test
    void moveStudent() {
        prepareTestGame(false);
        Island island = testGame.getBoard().getIslands().get(5);
        PlayerBoard playerBoard = testGame.getBoard().getPlayers().get(0).getPlayerBoard();
        Student student = Student.BLUE;

        island.getStudents().clear();
        island.addStudent(student);

        testController.moveStudent(island, Student.BLUE, playerBoard);

        assertTrue(playerBoard.getEntrance().contains(Student.BLUE));
    }

    @Test
    void firstPlayer() {
        prepareTestGame(false);

        testController.firstPlayer();

        assertTrue(testController.currentPlayer() != null);
        assertTrue(testGame.getBoard().getPlayers().contains(testGame.getBoard().getPlayerByNickname(testController.getCurrentPlayerNickname())));
    }

    @Test
    void nextPlayer() {
        prepareTestGame(false);

        testController.setCurrentPlayer("FirstPlayer");

        testController.nextPlayer();

        assertEquals("SecondPlayer", testController.getCurrentPlayerNickname());

        testController.nextPlayer();

        assertEquals("FirstPlayer", testController.getCurrentPlayerNickname());
    }

    @Test
    void playCard(){
        prepareTestGame(false);

        testController.setCurrentPlayer("FirstPlayer");

        Player player = testController.getCurrentPlayer();

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            testController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        assertEquals(9, player.getDeck().size());
        assertEquals(assistantCard, testController.getTurnCardsPlayed().get(0));
    }

    @Test
    void activateCharacter(){
        prepareTestGame(true);

        Character character = testGame.getCharacters()[0];
        int cost = character.getCost();
        Player player = testGame.getBoard().getPlayers().get(0);

        player.setNumCoins(cost);

        testController.setCurrentPlayer(player.getNickname());

        try{
            testController.activateCharacter(character.getEffectId());
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
        Player player = testGame.getBoard().getPlayers().get(0);
        player.setNumCoins(0);

        testController.setCurrentPlayer(player.getNickname());

        try {
            testController.activateCharacter(character.getEffectId());
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

        Player player = testGame.getBoard().getPlayers().get(0);
        player.setNumCoins(character.getCost());

        testController.setCurrentPlayer(player.getNickname());

        try {
            testController.activateIslandCharacter(3, chosenIsland);
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

        Player player = testGame.getBoard().getPlayers().get(0);
        player.setNumCoins(character.getCost());

        testController.setCurrentPlayer(player.getNickname());

        try {
            testController.activateStudentCharacter(1, chosenStudent);
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

        Player player1 = testGame.getBoard().getPlayers().get(0);
        Player player2 = testGame.getBoard().getPlayers().get(1);

        AssistantCard assistantCard1 = player1.getDeck().get(1);
        AssistantCard assistantCard2 = player2.getDeck().get(5);

        testController.setCurrentPlayer("FirstPlayer");

        try {
            testController.playCard(assistantCard1);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        nextPlayer();

        try {
            testController.playCard(assistantCard2);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        testController.setCurrentPlayer();
        assertEquals("FirstPlayer", testController.getCurrentPlayerNickname());
    }

    @Test
    void moveMotherNature() {
        prepareTestGame(false);

        Player player = testGame.getBoard().getPlayers().get(0);

        testController.setCurrentPlayer(player.getNickname());

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            testController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        int prevMotherNature = testGame.getBoard().getMotherNature();

        try {
            testController.moveMotherNature(assistantCard.getMaxMotherNatureMoves()-1);
        } catch (InvalidMotherNatureMovesException e) {
            e.printStackTrace();
        }
        int moves = assistantCard.getMaxMotherNatureMoves()-1;

        assertEquals(prevMotherNature+moves, testGame.getBoard().getMotherNature());
    }

    @Test
    void moveMotherNatureFail() {
        prepareTestGame(false);

        Player player = testGame.getBoard().getPlayers().get(0);

        AssistantCard assistantCard = player.getDeck().get(0);

        testController.setCurrentPlayer(player.getNickname());

        try {
            testController.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        int prevMotherNature = testGame.getBoard().getMotherNature();

        try {
            testController.moveMotherNature(assistantCard.getMaxMotherNatureMoves()+1);
        } catch (InvalidMotherNatureMovesException e) {
        }

        assertEquals(prevMotherNature, testGame.getBoard().getMotherNature());
    }
}