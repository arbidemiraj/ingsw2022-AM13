package it.polimi.ingsw.effects;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Island;
import it.polimi.ingsw.Student;

import java.util.ArrayList;

public class Effect1 implements Effect {

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
		//move chosen students
	}

	@Override
	public void apply(Game game, Island chosenIsland) {

	}

	@Override
	public void apply(Game game) {
	}
}
