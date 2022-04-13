package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    void fillEntrance() {
        GameTable gameTable = new GameTable(2);
        ArrayList<Student> students;
        PlayerBoard playerBoard = new PlayerBoard();

        students = gameTable.extractStudents(5);

        playerBoard.fillEntrance(students);

        assertEquals(students, playerBoard.getEntrance());
    }

    @Test
    void fillDinnerRoom() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Student> students = new ArrayList<>();

        for( int i = 0; i < 5; i++){
            students.add(Student.YELLOW);
        }
        for( int i = 0; i < 3; i++){
            students.add(Student.BLUE);
        }

        playerBoard.fillDinnerRoom(students);

        assertEquals(5, playerBoard.getDinnerRoom()[0].getNumStudents());
        assertEquals(3, playerBoard.getDinnerRoom()[1].getNumStudents());
    }
}