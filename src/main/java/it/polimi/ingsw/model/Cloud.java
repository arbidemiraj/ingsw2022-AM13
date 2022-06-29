package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyCloudException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the clouds
 */
public class Cloud implements Serializable {
	@Serial
	private static final long serialVersionUID = -2908368553595866575L;
	private ArrayList<Student> students;

	/**
	 * Default constructor
	 * @param numPlayers	number of players in the game
	 */
	public Cloud(int numPlayers) {
		if(numPlayers == 2) students = new ArrayList<>(3);
		if(numPlayers == 3) students = new ArrayList<>(4);

	}

	/**
	 * Adds student to the cloud
	 * @param students	students to add to the cloud
	 */
	public void addStudents(List<Student> students) {
		this.students.addAll(students);
	}

	/**
	 * Extracts the students from the cloud
	 * @return the students extracted
	 * @throws EmptyCloudException when the cloud is empty
	 */
	public ArrayList<Student> getStudentsFromCloud() throws EmptyCloudException {
		ArrayList<Student> stud = new ArrayList<>();
		stud.addAll(students);


		if(students.isEmpty()) throw new EmptyCloudException();
		else students.removeAll(stud);

		return stud;
	}

	public List<Student> getStudents() {
		return students;
	}
}
