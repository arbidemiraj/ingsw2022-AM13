package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Island implements Movable {

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

	@Override
	public Student removeStudent(Student color) throws InvalidMoveException {
		Student student;

		if(!students.contains(color)) throw new InvalidMoveException();

		for(int i = 0; i < students.size(); i ++){
			student = students.get(i);
			if(student.equals(color)) return student;
		}

		return null;
	}
	@Override
	public void addStudent(Student student) {
		students.add(student);
	}

	public int[] getNumStudents() {
		int[] numStudents = new int[5];
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

