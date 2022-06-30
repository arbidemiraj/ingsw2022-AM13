package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    void fillEntrance() {
        GameBoard gameBoard = new GameBoard(2);
        gameBoard.prepareGame();
        ArrayList<Student> students = null;
        PlayerBoard playerBoard = new PlayerBoard();

        try {
            students = gameBoard.extractStudents(5);
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }

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

    @Test
    void removeStudent() {
        PlayerBoard playerBoard = new PlayerBoard();

        playerBoard.addStudent(Student.YELLOW);
        Student student = null;

        try {
            student = playerBoard.removeStudent(Student.YELLOW);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(Student.YELLOW, student);
        assertEquals(0, playerBoard.getEntrance().size());
    }

    @Test
    void addStudent() {
        PlayerBoard playerBoard = new PlayerBoard();

        playerBoard.addStudent(Student.YELLOW);

        assertEquals(Student.YELLOW, playerBoard.getEntrance().get(0));
    }

    @Test
    void getEntranceStudents() {
        PlayerBoard playerBoard = new PlayerBoard();

        playerBoard.addStudent(Student.BLUE);

        playerBoard.addStudent(Student.YELLOW);

        int numStud[] = {1, 1, 0, 0, 0};

        assertEquals(numStud[0], playerBoard.getEntranceStudents()[0]);
        assertEquals(numStud[1], playerBoard.getEntranceStudents()[1]);
    }
}