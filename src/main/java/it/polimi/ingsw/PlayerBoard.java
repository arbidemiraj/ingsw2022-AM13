package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {

	private ArrayList<Student> entrance = new ArrayList<>();

	private DinnerRoomRow[] dinnerRoom = new DinnerRoomRow[5];

	public PlayerBoard(){
		dinnerRoom[0] = new DinnerRoomRow(Student.YELLOW);
		dinnerRoom[1] = new DinnerRoomRow(Student.BLUE);
		dinnerRoom[2] = new DinnerRoomRow(Student.GREEN);
		dinnerRoom[3] = new DinnerRoomRow(Student.PINK);
		dinnerRoom[4] = new DinnerRoomRow(Student.RED);
	}

	public void fillEntrance(List<Student> students) {
		for(Student student:students){
			entrance.add(student);
		}
	}
	public void fillDinnerRoom(List<Student> students) {
		for(Student student:students){
			if(student == Student.YELLOW) dinnerRoom[0].addStudent();
			if(student == Student.BLUE) dinnerRoom[1].addStudent();
			if(student == Student.GREEN) dinnerRoom[2].addStudent();
			if(student == Student.PINK) dinnerRoom[3].addStudent();
			if(student == Student.RED) dinnerRoom[4].addStudent();
		}

	}

	public DinnerRoomRow[] getDinnerRoom() {
		return dinnerRoom;
	}

	public ArrayList<Student> getEntrance() {
		return entrance;
	}
}
