package it.polimi.ingsw;

public class DinnerRoomRow {

	private Student color;

	private int numStudents;

	public Student getColor() {
		return color;
	}

	public void addStudent() {numStudents++;}

	public int getNumStudents() {
		return numStudents;
	}

	public DinnerRoomRow(Student color) {
		this.color = color;
	}

}
