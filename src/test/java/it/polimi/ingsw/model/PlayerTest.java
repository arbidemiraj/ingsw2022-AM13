package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playCard() {
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
}