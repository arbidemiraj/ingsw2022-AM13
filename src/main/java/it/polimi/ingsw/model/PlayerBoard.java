package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerBoard implements Movable {

	private ArrayList<Student> entrance = new ArrayList<>();

	private DinnerRoomRow[] dinnerRoom = new DinnerRoomRow[5];

	public PlayerBoard(){
		IntColorMap studentColorMap = new IntColorMap();
		HashMap<Integer, Student> studentColor = studentColorMap.getMap();

		for(int i = 0; i < 5; i++){
			dinnerRoom[i] = new DinnerRoomRow(studentColor.get(i));
		}
	}

	public void fillEntrance(List<Student> students) {
		for(Student student:students){
			entrance.add(student);
		}
	}
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

	public int[] getEntranceStudents(){
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();
		int[] entranceStud = {0, 0, 0, 0, 0};

		for(Student student : entrance){
			entranceStud[studentColor.get(student)]++;
		}

		return entranceStud;
	}
	@Override
	public Student removeStudent(Student color) {
		Student student = null;

		for(int i = 0; i < entrance.size(); i++){
			if(entrance.get(i).equals(color)){
				student = entrance.get(i);
				entrance.remove(student);
				return student;
			}

		}

		return null;
	}

	@Override
	public void addStudent(Student student) {
		entrance.add(student);
	}
}
