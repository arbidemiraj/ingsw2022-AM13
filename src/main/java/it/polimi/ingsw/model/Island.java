package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * The class that represents the island
 */
public class Island implements Movable {

	private ArrayList<Student> students;

	private Player owner;

	private IslandState islandState;

	private boolean noEntryTile;

	/**
	 * Default constructor
	 */
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

	/**
	 * changes the state if an island is unified
	 * @param islandState the state you want to change the island in
	 */
	public void changeState(IslandState islandState) {
		this.islandState = islandState;
	}

	public IslandState getIslandState() {
		return islandState;
	}

	/**
	 * removes the given student from the island
	 * @param color the color of the student you want to remove
	 * @return the student that has been removed
	 * @throws InvalidMoveException if the given student isn't in the island
	 */
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

	/**
	 * adds a student to the island
	 * @param student the student you want to add to the island
	 */
	@Override
	public void addStudent(Student student) {
		students.add(student);
	}

	/**
	 * return an array containing the number of students of each color
	 * @return an array containing the number of students of each color
	 */
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

	/**
	 * returns true if the island has a no entry tile on it
	 * @return
	 */
	public boolean isNoEntryTile() {
		return noEntryTile;
	}

	/**
	 * removes the no entry tile
	 */
    public void removeNoEntryTile() {
		noEntryTile = false;
    }
}

