package it.polimi.ingsw;

import javax.swing.*;
import java.awt.*;
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

	public void changeState(IslandState islandState) {
		this.islandState = islandState;
	}

	public IslandState getIslandState() {
		return islandState;
	}

	public void addStudent(Student student) {
		students.add(students.size() - 1, student);
	}

	public int[] getNumStudents() {
		int numStudents[] = new int[5];

		for (Student student : students) {
			if (student.equals(Student.YELLOW)) numStudents[0]++;
			if (student.equals(Student.BLUE)) numStudents[1]++;
			if (student.equals(Student.GREEN)) numStudents[2]++;
			if (student.equals(Student.PINK)) numStudents[3]++;
			if (student.equals(Student.RED)) numStudents[4]++;
		}

		return numStudents;
	}

	public Player getOwner() {
		return owner;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
}

