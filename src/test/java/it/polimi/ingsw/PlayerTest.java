package it.polimi.ingsw;

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
        ArrayList<Student> students = new ArrayList<>();
        int numStud;

        for(int i = 0; i < 4; i++){
            students.add(Student.YELLOW);
        }

        game.getTable().getPlayers()[0].getPlayerBoard().fillDinnerRoom(students);

        numStud = game.getTable().getPlayers()[0].numStudents();

        assertEquals(4, numStud);

    }
}