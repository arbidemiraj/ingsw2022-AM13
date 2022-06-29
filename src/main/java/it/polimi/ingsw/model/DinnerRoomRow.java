package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

/**
 * Class that manages a single row of the dinner room
 */
public class DinnerRoomRow implements Movable {

	private final Student color;

	private int numStudents;

	public Student getColor() {
		return color;
	}

	public int getNumStudents() {
		return numStudents;
	}

	/**
	 * Default constructor
	 * @param color	the color of the row
	 */
	public DinnerRoomRow(Student color) {
		this.color = color;
	}

	/**
	 * removes a student from the row
	 * @param color	the color of the student to remove
	 * @return
	 * @throws InvalidMoveException	when the student passed doesn't exist
	 */
	@Override
	public Student removeStudent(Student color) throws InvalidMoveException {
		if(numStudents == 0) throw new InvalidMoveException();

		numStudents--;

		return color;
	}

	/**
	 * adds a student to the row
	 * @param student	the color of the student to add
	 */
	@Override
	public void addStudent(Student student) {
		numStudents++;
	}
}
