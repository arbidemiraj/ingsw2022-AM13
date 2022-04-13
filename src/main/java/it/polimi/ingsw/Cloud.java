package it.polimi.ingsw;

import java.util.ArrayList;



public class Cloud {
	private ArrayList<Student> students;

	public Cloud(int numPlayers) {
		if(numPlayers == 2) students = new ArrayList<>(3);
		if(numPlayers == 3) students = new ArrayList<>(4);

	}

	public void addStudents(ArrayList<Student> students) {
		this.students.addAll(students);
	}

	//gets the student from the cloud, removing them from the cloud
	public ArrayList<Student> getStudentsFromCloud() throws EmptyCloudException {
		ArrayList<Student> stud = new ArrayList<>();
		stud.addAll(students);


		if(students.isEmpty()) throw new EmptyCloudException();
		else students.removeAll(students);

		return stud;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
}
