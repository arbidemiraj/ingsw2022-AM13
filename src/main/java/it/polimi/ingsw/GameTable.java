package it.polimi.ingsw;

import java.util.ArrayList;

public class GameTable {

	private Player[] players;

	private ArrayList<Island> islands;

	private ArrayList<Student> bag;

	private Cloud[] clouds;

	private int motherNature;

	private Professor[] professors;

	public GameTable (int numPlayers) {
		islands = new ArrayList<>(12);
		for (int i=0; i<12; i++) islands.add(i, new Island());

		motherNature = (int) (Math.random()*12);

		//Initial 10 students in the bag
		initBag();

		//Create islands
		fillIslands();

		//Fill the bag with 120 students remaining
		fillBag();

		//Create instances for clouds
		clouds[0] = new Cloud();
		clouds[1] = new Cloud();

		//Creates 5 professors
		Professor yellow = new Professor(Student.YELLOW);
		Professor blue = new Professor(Student.BLUE);
		Professor green = new Professor(Student.GREEN);
		Professor pink = new Professor(Student.PINK);
		Professor red = new Professor(Student.RED);

		//fill entrance for each player
		for(int i = 0; i < numPlayers; i++) {
			players[i].getPlayerBoard().fillEntrance(extractStudents(7));
		}
	}


	public Cloud[] getClouds() {
		return clouds;
	}

	private void initBag() {
		bag = new ArrayList<Student>();

		for(int i = 0; i < 10; i=i+5){
			bag.add(i, Student.YELLOW);
			bag.add(i+1, Student.BLUE);
			bag.add(i+2, Student.GREEN);
			bag.add(i+3, Student.PINK);
			bag.add(i+4, Student.RED);
		}
	}

	public void fillBag() {
		for(int i = 0; i < 120; i=i+5){
			bag.add(i, Student.YELLOW);
			bag.add(i+1, Student.BLUE);
			bag.add(i+2, Student.GREEN);
			bag.add(i+3, Student.PINK);
			bag.add(i+4, Student.RED);
		}
	}

	private void fillIslands() {
		int random;
		int i = 10;
		int j = 0;

		while(i > 0){
			random = (int)(Math.random()*10);

			if((islands.get(random).getStudents()) == null){
				islands.get(random).addStudent(extractStudents(10).get(j));
				i--;
				j++;
			}
		}
	}

	public Professor[] getProfessors() {
		return professors;
	}

	public ArrayList<Student> extractStudents(int numStudents) {
		ArrayList<Student> extractedStudents = new ArrayList<>();

		int random;

		for(int i = 0; i < numStudents; i++){
			random = (int)(Math.random()*bag.size());
			extractedStudents.add(i, bag.get(random));
			bag.remove(random);
		}

		return extractedStudents;
	}

	public ArrayList<Student> getBag() {
		return bag;
	}

	public int getMotherNature() {
		return motherNature;
	}

	public ArrayList<Island> getIslands() {
		return islands;
	}

	public void moveMotherNature(int steps) {
		motherNature += steps;
	}

	//returns the island with mother nature in it
	public Island getMotherNatureIsland() {
		return islands.get(motherNature);
	}

	public Player[] getPlayers() {
		return players;
	}

	public void moveStudentsFromCloud(int numCloud, int currentPlayer){
		ArrayList<Student> students = new ArrayList<>();

		try{
			students = clouds[numCloud].getStudents();
		} catch (EmptyCloudException e) {
			e.printStackTrace();
		}

		players[currentPlayer].getPlayerBoard().fillEntrance(students);
	}
}
