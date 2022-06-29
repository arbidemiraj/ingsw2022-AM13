package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

/**
 * The effect number 7 class
 */
public class Effect7 implements Movable, Actionable {
    private ArrayList<Student> students;

    /**
     * Default constructor
     * @param students the students on the card
     */
    public Effect7(ArrayList<Student> students){
        this.students = students;
    }

    /**
     * Removes a student of the given color
     * @param color the color of the student you want to get
     * @return the student removed
     */
    @Override
    public Student removeStudent(Student color) {
        students.remove(color);

        return color;
    }

    @Override
    public void apply(Game game, Student chosenStudent) {
        removeStudent(chosenStudent);

    }

    /**
     * adds a student
     * @param student the student you want to add
     */
    @Override
    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public void apply(Game game) {

    }

    @Override
    public void apply(Game game, Island chosenIsland) {

    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
