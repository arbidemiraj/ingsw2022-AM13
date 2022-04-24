package it.polimi.ingsw.model;

import it.polimi.ingsw.model.DinnerRoomRow;
import it.polimi.ingsw.model.enumerations.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DinnerRoomRowTest {

    @Test
    void getColor() {
        DinnerRoomRow dinnerRoomRow = new DinnerRoomRow(Student.YELLOW);

        assertEquals(Student.YELLOW, dinnerRoomRow.getColor());
    }

    @Test
    void addStudent() {
        DinnerRoomRow dinnerRoomRow = new DinnerRoomRow(Student.YELLOW);

        dinnerRoomRow.addStudent(Student.YELLOW);

        assertEquals(1, dinnerRoomRow.getNumStudents());
    }
}