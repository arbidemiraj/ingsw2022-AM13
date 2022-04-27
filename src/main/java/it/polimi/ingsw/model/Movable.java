package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;

public interface Movable {

    Student removeStudent(Student color);
    void addStudent(Student student);
}
