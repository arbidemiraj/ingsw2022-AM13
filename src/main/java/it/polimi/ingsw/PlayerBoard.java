package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerBoard {

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
			dinnerRoom[studentColor.get(student)].addStudent();
		}

	}

	public DinnerRoomRow[] getDinnerRoom() {
		return dinnerRoom;
	}

	public ArrayList<Student> getEntrance() {
		return entrance;
	}
}
