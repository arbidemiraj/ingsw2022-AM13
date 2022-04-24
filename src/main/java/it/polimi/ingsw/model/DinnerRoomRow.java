package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;

public class DinnerRoomRow implements Move{

	private final Student color;

	private int numStudents;

	public Student getColor() {
		return color;
	}

	public int getNumStudents() {
		return numStudents;
	}

	public DinnerRoomRow(Student color) {
		this.color = color;
	}

	@Override
	public Student removeStudent(int position) {
		numStudents--;
		Student student = this.color;

		return student;
	}

	@Override
	public void addStudent(Student student) {
		numStudents++;
	}
}
