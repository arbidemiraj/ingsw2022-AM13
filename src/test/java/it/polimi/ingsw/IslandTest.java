package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    @Test
    void addStudent() {
        Island island = new Island();
        Student student = Student.BLUE;

        island.addStudent(student);

        assertEquals(student, island.getStudents().get(0));
    }

    @Test
    void getNumStudents() {
        Island island = new Island();

        Student[] students = {Student.YELLOW, Student.YELLOW,
                Student.BLUE,
                Student.GREEN, Student.GREEN,
                Student.RED};

        for(Student student : students){
            island.addStudent(student);
        }

        assertEquals(2, island.getNumStudents()[0]);
        assertEquals(1, island.getNumStudents()[1]);
        assertEquals(2, island.getNumStudents()[2]);
        assertEquals(0, island.getNumStudents()[3]);
        assertEquals(1, island.getNumStudents()[4]);
    }
}