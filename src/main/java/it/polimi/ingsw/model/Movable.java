package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

/**
 * Interface implemented by the objects which can have students on it
 */
public interface Movable {

    /**
     * removes a student
     * @param color the color of the student you want to get
     * @return the student you want
     * @throws InvalidMoveException if the student asked doesn't exist
     */
    Student removeStudent(Student color) throws InvalidMoveException;

    /**
     * adds a student
     * @param student the student you want to add
     */
    void addStudent(Student student);
}
