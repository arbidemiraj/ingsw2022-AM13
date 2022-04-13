package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;

public class Island {

	private ArrayList<Student> students;

	private Player owner;

	private IslandState islandState;

	private boolean noEntryTile;

	public Island() {
		students = new ArrayList<>();
		owner = null;
		islandState = new SingleIsland();
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void changeState(IslandState islandState) {
		this.islandState = islandState;
	}

	public IslandState getIslandState() {
		return islandState;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public int[] getNumStudents() {
		int numStudents[] = new int[5];
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		for (Student student : students) {
				numStudents[studentColor.get(student)]++;
			}

		return numStudents;
	}

	public Player getOwner() {
		return owner;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public void setNoEntryTile() {
		this.noEntryTile = true;
	}

	public boolean isNoEntryTile() {
		return noEntryTile;
	}
}

