package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

public interface Movable {

    Student removeStudent(Student color) throws InvalidMoveException;
    void addStudent(Student student);
}
