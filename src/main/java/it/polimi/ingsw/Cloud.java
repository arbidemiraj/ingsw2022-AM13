package it.polimi.ingsw;

import java.util.ArrayList;



public class Cloud {
	private ArrayList<Student> students;

	public Cloud(int numPlayers) {
		if(numPlayers == 2) students = new ArrayList<>(3);
		if(numPlayers == 3) students = new ArrayList<>(4);
		System.out.println("Size "+ students.size());
	}

	public void addStudents(ArrayList<Student> students) {
		this.students.addAll(students);
	}

	public ArrayList<Student> getStudentsFromCloud() throws EmptyCloudException {
		ArrayList<Student> stud;
		stud = students;

		for ( int i = 0; i < students.size(); i++){
			if(students.isEmpty()) throw new EmptyCloudException();
			students.remove(i);
		}

		return stud;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
}
