package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that represents the player board
 */
public class PlayerBoard implements Movable {

	private ArrayList<Student> entrance = new ArrayList<>();

	private DinnerRoomRow[] dinnerRoom = new DinnerRoomRow[5];

	/**
	 * Default constructor
	 */
	public PlayerBoard(){
		IntColorMap studentColorMap = new IntColorMap();
		HashMap<Integer, Student> studentColor = studentColorMap.getMap();

		for(int i = 0; i < 5; i++){
			dinnerRoom[i] = new DinnerRoomRow(studentColor.get(i));
		}
	}

	/**
	 * Fills the entrance with the given students
	 * @param students the students you want to put in the entrance
	 */
	public void fillEntrance(List<Student> students) {
		for(Student student:students){
			entrance.add(student);
		}
	}

	/**
	 * fills the dinner room with the given students
	 * @param students the students you want to add to the dinner room
	 */
	public void fillDinnerRoom(List<Student> students) {
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		for(Student student:students){
			dinnerRoom[studentColor.get(student)].addStudent(student);
		}

	}

	public DinnerRoomRow[] getDinnerRoom() {
		return dinnerRoom;
	}

	public ArrayList<Student> getEntrance() {
		return entrance;
	}

	/**
	 * returns an array containing the number of each student in the entrance
	 * @return an array containing the number of each student in the entrance
	 */
	public int[] getEntranceStudents(){
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();
		int[] entranceStud = {0, 0, 0, 0, 0};

		for(Student student : entrance){
			entranceStud[studentColor.get(student)]++;
		}

		return entranceStud;
	}

	/**
	 * removes a student
	 * @param color the color of the student you want to get
	 * @return the student you want
	 * @throws InvalidMoveException if the student asked doesn't exist
	 */
	@Override
	public Student removeStudent(Student color) throws InvalidMoveException {
		Student student = null;

		if(!entrance.contains(color)) throw new InvalidMoveException();

		for(int i = 0; i < entrance.size(); i++){
			if(entrance.get(i).equals(color)){
				student = entrance.get(i);
				entrance.remove(student);
				return student;
			}

		}

		return null;
	}

	/**
	 * adds a student
	 * @param student the student you want to add
	 */
	@Override
	public void addStudent(Student student) {
		entrance.add(student);
	}
}
