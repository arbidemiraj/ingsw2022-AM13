package it.polimi.ingsw;

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

        dinnerRoomRow.addStudent();

        assertEquals(1, dinnerRoomRow.getNumStudents());
    }
}