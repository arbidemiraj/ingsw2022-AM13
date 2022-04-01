package it.polimi.ingsw;

import java.util.ArrayList;

public class Island {

	private ArrayList<Student> students;

	private Player owner;

	private IslandState islandState;

	public Island() {
		students = new ArrayList<>();
		owner = null;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void changeState() {
		islandState = new UnifiedIsland();
	}

	public boolean controllingCheck() {
		return false;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

}
