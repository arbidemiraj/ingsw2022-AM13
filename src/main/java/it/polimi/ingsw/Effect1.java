package it.polimi.ingsw;

import java.util.ArrayList;

public class Effect1 implements StudentEffect{

	private int id;
	private ArrayList<Student> students;

	public Effect1(int id, ArrayList<Student> students) {
		this.id = id;
		this.students = students;
	}

	public void apply(Game game, Student chosenStudent) {
		//move chosen students
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public int getId() {
		return id;
	}


	@Override
	public void apply(Game game) {
	}
}
