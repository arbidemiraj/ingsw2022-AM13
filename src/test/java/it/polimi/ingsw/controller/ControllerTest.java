package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void endingConditionCheck1() {
        Game game = new Game(2, false);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);

        game.getBoard().prepareGame();

        Controller gameController = new Controller(game);

        game.getBoard().getBag().clear();

        assertTrue(gameController.endingConditionCheck());
    }

    @Test
    void endingConditionCheck2() {
        Game game = new Game(2, false);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);

        game.getBoard().prepareGame();

        Controller gameController = new Controller(game);

        game.getBoard().getPlayers().get(0).setNumTowers(0);

        assertTrue(gameController.endingConditionCheck());
    }

    @Test
    void moveStudent() {
        Game game = new Game(2, false);
        game.getBoard().prepareGame();
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        Island island = game.getBoard().getIslands().get(5);
        PlayerBoard playerBoard = game.getBoard().getPlayers().get(0).getPlayerBoard();
        Student student = Student.BLUE;

        island.getStudents().clear();
        island.addStudent(student);

        Controller controller = new Controller(game);

        controller.moveStudent(island, Student.BLUE, playerBoard);

        assertTrue(playerBoard.getEntrance().contains(Student.BLUE));
    }

    @Test
    void firstPlayer() {
        Game game = new Game(2, false);
        game.getBoard().prepareGame();
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        Controller controller = new Controller(game);

        controller.firstPlayer();

        assertTrue(controller.currentPlayer() != null);
        assertTrue(game.getBoard().getPlayers().contains(game.getBoard().getPlayerByNickname(controller.getCurrentPlayerNickname())));
    }

    @Test
    void nextPlayer() {
    }

    @Test
    void playCard(){
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);
        game.getBoard().prepareGame();


        Controller controller = new Controller(game);
        controller.setCurrentPlayer("FirstPlayer");

        Player player = controller.getCurrentPlayer();

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            controller.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        assertEquals(9, player.getDeck().size());
        assertEquals(assistantCard, controller.getTurnCardsPlayed().get(0));
    }

    @Test
    void activateCharacter(){
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);
        game.getBoard().prepareGame();

        Controller controller = new Controller(game);

        it.polimi.ingsw.model.Character character = game.getCharacters()[0];
        int cost = character.getCost();
        Player player = game.getBoard().getPlayers().get(0);

        player.setNumCoins(cost);

        controller.setCurrentPlayer(player.getNickname());

        try{
            controller.activateCharacter(character.getEffectId());
        } catch (NotEnoughCoinException e) {
            e.printStackTrace();
        }


        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(game.getActivatedCharacters().contains(character.getEffectId()));
    }

    @Test
    void activateCharacterFail(){
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);

        game.getBoard().prepareGame();
        Controller controller = new Controller(game);

        Character character = game.getCharacters()[0];
        Player player = game.getBoard().getPlayers().get(0);
        player.setNumCoins(0);

        controller.setCurrentPlayer(player.getNickname());

        try {
            controller.activateCharacter(character.getEffectId());
        }
        catch(NotEnoughCoinException e) {
        }


        assertNull(character.getOwner());
        assertFalse(character.isActivated());
    }

    @Test
    void activateIslandCharacter() {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().prepareGame();

        Island chosenIsland = game.getBoard().getIslands().get(3);
        Controller controller = new Controller(game);

        Character character = new Character(game, 3);
        game.getCharacters()[0] = character;

        Player player = game.getBoard().getPlayers().get(0);
        player.setNumCoins(character.getCost());

        controller.setCurrentPlayer(player.getNickname());

        try {
            controller.activateIslandCharacter(3, chosenIsland);
        }
        catch(NotEnoughCoinException e) {
        }


        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(game.getActivatedCharacters().contains(3));
    }

    @Test
    void activateStudentCharacter() {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().prepareGame();

        Student chosenStudent = Student.YELLOW;
        Controller controller = new Controller(game);

        Character character = new Character(game, 1);
        game.getCharacters()[0] = character;

        Player player = game.getBoard().getPlayers().get(0);
        player.setNumCoins(character.getCost());

        controller.setCurrentPlayer(player.getNickname());

        try {
            controller.activateStudentCharacter(1, chosenStudent);
        }
        catch(NotEnoughCoinException e) {
        }


        assertEquals(character.getOwner(), player);
        assertTrue(character.isActivated());
        assertTrue(game.getActivatedCharacters().contains(1));
    }

    @Test
    void setCurrentPlayer() {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().addPlayer("SecondPlayer", TowerColor.BLACK);
        game.getBoard().prepareGame();


        Controller controller = new Controller(game);

        Player player1 = game.getBoard().getPlayers().get(0);
        Player player2 = game.getBoard().getPlayers().get(1);

        AssistantCard assistantCard1 = player1.getDeck().get(1);
        AssistantCard assistantCard2 = player2.getDeck().get(5);

        controller.setCurrentPlayer("FirstPlayer");

        try {
            controller.playCard(assistantCard1);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        nextPlayer();

        try {
            controller.playCard(assistantCard2);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        controller.setCurrentPlayer();
        assertEquals("FirstPlayer", controller.getCurrentPlayerNickname());
    }

    @Test
    void moveMotherNature() {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().prepareGame();

        Controller controller = new Controller(game);
        Player player = game.getBoard().getPlayers().get(0);

        controller.setCurrentPlayer(player.getNickname());

        AssistantCard assistantCard = player.getDeck().get(0);

        try {
            controller.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        int prevMotherNature = game.getBoard().getMotherNature();

        try {
            controller.moveMotherNature(assistantCard.getMaxMotherNatureMoves()-1);
        } catch (InvalidMotherNatureMovesException e) {
            e.printStackTrace();
        }
        int moves = assistantCard.getMaxMotherNatureMoves()-1;

        assertEquals(prevMotherNature+moves, game.getBoard().getMotherNature());
    }

    @Test
    void moveMotherNatureFail() {
        Game game = new Game(2, true);
        game.getBoard().addPlayer("FirstPlayer", TowerColor.GRAY);
        game.getBoard().prepareGame();

        Controller controller = new Controller(game);
        Player player = game.getBoard().getPlayers().get(0);

        AssistantCard assistantCard = player.getDeck().get(0);

        controller.setCurrentPlayer(player.getNickname());

        try {
            controller.playCard(assistantCard);
        }catch (CardAlreadyPlayedException e){
            e.printStackTrace();
        }

        int prevMotherNature = game.getBoard().getMotherNature();

        try {
            controller.moveMotherNature(assistantCard.getMaxMotherNatureMoves()+1);
        } catch (InvalidMotherNatureMovesException e) {
        }

        assertEquals(prevMotherNature, game.getBoard().getMotherNature());
    }
}