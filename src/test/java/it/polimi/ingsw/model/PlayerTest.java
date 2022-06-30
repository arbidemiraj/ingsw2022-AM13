package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playCard() {
        Game game = new Game(2, false);
        game.addPlayer("FirstPlayer");

        Player player = game.getPlayers().get(0);

        AssistantCard card = player.getAssistantCardById(1);

        player.playCard(card);

        assertFalse(player.getDeck().contains(card));
        assertEquals(card.getMaxMotherNatureMoves(), player.getMotherNatureMoves());
    }

    @Test
    void numStudents(){
        Game game = new Game(2, false);
        game.addPlayer("FirstPlayer");
        game.addPlayer("SecondPlayer");

        game.getBoard().prepareGame();
        ArrayList<Student> students = new ArrayList<>();
        int numStud;

        for(int i = 0; i < 4; i++){
            students.add(Student.YELLOW);
        }

        game.getPlayers().get(0).getPlayerBoard().fillDinnerRoom(students);

        numStud = game.getPlayers().get(0).numStudents();

        assertEquals(4, numStud);

    }

    @Test
    void addCoin() {
        Game game = new Game(2, false);
        game.addPlayer("FirstPlayer");

        Player player = game.getPlayers().get(0);

        player.setNumCoins(2);

        player.addCoin();

        assertEquals(3, player.getNumCoins());
    }

    @Test
    void getAssistantCardById() {
        Game game = new Game(2, false);
        game.addPlayer("FirstPlayer");

        Player player = game.getPlayers().get(0);

        AssistantCard card = player.getAssistantCardById(1);

        assertEquals(player.getDeck().get(0), card);
    }
}