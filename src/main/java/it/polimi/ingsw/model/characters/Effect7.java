package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

public class Effect7 implements Movable, Actionable {
    private ArrayList<Student> students;

    public Effect7(ArrayList<Student> students){
        this.students = students;
    }
    @Override
    public Student removeStudent(Student color) {
        students.remove(color);

        return color;
    }

    @Override
    public void apply(Game game, Student chosenStudent) {
        removeStudent(chosenStudent);
        game.getPlayers().get(game.getCurrentPlayer()).getPlayerBoard().addStudent(chosenStudent);
    }

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
