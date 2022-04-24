package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Game;
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
        Game game = new Game(2);
        game.getTable().addPlayer("FirstPlayer");
        game.getTable().addPlayer("SecondPlayer");

        game.getTable().prepareGame();
        ArrayList<Student> students = new ArrayList<>();
        int numStud;

        for(int i = 0; i < 4; i++){
            students.add(Student.YELLOW);
        }

        game.getTable().getPlayers().get(0).getPlayerBoard().fillDinnerRoom(students);

        numStud = game.getTable().getPlayers().get(0).numStudents();

        assertEquals(4, numStud);

    }
}