package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

public class DinnerRoomRow implements Movable {

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
	public Student removeStudent(Student color) throws InvalidMoveException {
		numStudents--;
		Student student = this.color;

		return color;
	}

	@Override
	public void addStudent(Student student) {
		numStudents++;
	}
}
