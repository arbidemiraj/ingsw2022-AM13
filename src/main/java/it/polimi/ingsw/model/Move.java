package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;

public interface Move {

    Student removeStudent(int position);
    void addStudent(Student student);
}
