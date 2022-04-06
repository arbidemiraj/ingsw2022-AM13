package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    @Test
    void testAddStudents() throws EmptyCloudException {
        Student s1 = Student.YELLOW;
        Student s2 = Student.YELLOW;
        Student s3 = Student.YELLOW;
        Cloud cloud = new Cloud(2);

        ArrayList<Student> students = new ArrayList<>();

        students.add(s1);
        students.add(s2);
        students.add(s3);
        System.out.println("Size "+ students);


        cloud.addStudents(students);

        assertEquals(s1, cloud.getStudents().get(0));
        assertEquals(s2, cloud.getStudents().get(1));
        assertEquals(s3, cloud.getStudents().get(2));
    }
}