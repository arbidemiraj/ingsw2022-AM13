package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

public class Effect1 implements Actionable {

	private int id;
	private ArrayList<Student> students;

	public Effect1(int id, ArrayList<Student> students) {
		this.id = id;
		this.students = students;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public int getId() {
		return id;
	}

	@Override
	public void apply(Game game, Student chosenStudent) {

	}

	@Override
	public void apply(Game game, Island chosenIsland) {

	}

	@Override
	public void apply(Game game) {
	}
}
