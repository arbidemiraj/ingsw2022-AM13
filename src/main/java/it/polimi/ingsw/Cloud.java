package it.polimi.ingsw;

import java.util.ArrayList;

public class Cloud {

	private ArrayList<Student> students;

	public void Cloud(int numPlayers) {
		if(numPlayers == 2) students = new ArrayList<>(3);
		if(numPlayers == 3) students = new ArrayList<>(4);
	}

	public void addStudents(ArrayList<Student> students) {
		this.students=students;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void removeStudents() throws EmptyCloudException {
		for ( int i = 0; i < students.size(); i++){
			if(students.isEmpty()) throw new EmptyCloudException();
			students.remove(i);
		}
	}
}
