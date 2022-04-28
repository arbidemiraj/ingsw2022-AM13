package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

public class Effect1 implements Actionable {

	private ArrayList<Student> students;
	private Student chosenStudent;

	public Effect1(ArrayList<Student> students) {
		this.students = students;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	@Override
	public void apply(Game game , Student chosenStudent) {
		this.chosenStudent = chosenStudent;
	}

	@Override
	public void apply(Game game, Island chosenIsland) {
		chosenIsland.addStudent(chosenStudent);
		students.remove(chosenStudent);
		students.add(game.getBoard().extractStudents(1).get(0));
	}

	@Override
	public void apply(Game game ) {
	}
}
