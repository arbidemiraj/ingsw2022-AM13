package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    @Test
    void addStudents() {
        Student s1 = Student.YELLOW;
        Student s2 = Student.YELLOW;
        Student s3 = Student.YELLOW;
        Cloud cloud = new Cloud(2);

        ArrayList<Student> students = new ArrayList<>();

        students.add(s1);
        students.add(s2);
        students.add(s3);

        cloud.addStudents(students);

        assertEquals(s1, cloud.getStudents().get(0));
        assertEquals(s2, cloud.getStudents().get(1));
        assertEquals(s3, cloud.getStudents().get(2));
    }

    @Test
    void getStudentsFromCloud() throws EmptyCloudException {
        ArrayList<Student> students;
        Cloud cloud = new Cloud(2);
        Student s1 = Student.YELLOW;
        Student s2 = Student.YELLOW;
        Student s3 = Student.YELLOW;

        cloud.getStudents().add(s1);
        cloud.getStudents().add(s2);
        cloud.getStudents().add(s3);

        students = cloud.getStudentsFromCloud();

        assertEquals(students.get(0), s1);
        assertEquals(students.get(1), s2);
        assertEquals(students.get(2), s3);

        assertTrue(cloud.getStudents().isEmpty());
    }
}