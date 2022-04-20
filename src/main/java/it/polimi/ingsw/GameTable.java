package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;

public class GameTable {

	private final Player[] players;

	private DoublyLinkedList islands;

	private ArrayList<Student> bag;

	private Cloud[] clouds;

	private int motherNature;

	private final Professor[] professors;

	public GameTable (int numPlayers) {
		motherNature = (int) (Math.random()*11);
		players = new Player[numPlayers];

		TowerColor[] towerColor = {TowerColor.BLACK, TowerColor.GRAY, TowerColor.WHITE};
		int numTowers;

		if(numPlayers == 3) numTowers = 6;
		else numTowers = 8;

		for(int i = 0; i < numPlayers; i++){
			this.players[i] = new Player(towerColor[i], numTowers);
		}

		islands = new DoublyLinkedList();

		for (int i=0; i<12; i++){
			Island island = new Island();
			islands.add(island);
		}

		//Initial 10 students in the bag
		initBag();

		//Create islands
		fillIslands();

		//Fill the bag with 120 students remaining
		fillBag();

		clouds = new Cloud[2];

		//Create instances for clouds
		clouds[0] = new Cloud(numPlayers);
		clouds[1] = new Cloud(numPlayers);

		fillClouds();

		//Creates 5 professors
		IntColorMap studentColorMap = new IntColorMap();
		HashMap<Integer, Student> studentColor = studentColorMap.getMap();

		professors = new Professor[5];

		for(int i = 0; i < 5; i++){
			professors[i] = new Professor(studentColor.get(i));
		}

		//fill entrance for each player
		for(Player player : players) {
			if(numPlayers == 2) player.getPlayerBoard().fillEntrance(extractStudents(7));
			if(numPlayers == 3) player.getPlayerBoard().fillEntrance(extractStudents(9));
		}
	}

	private void fillClouds() {
		int dim = players.length + 1;

		for(Cloud cloud : clouds){
			cloud.addStudents(extractStudents(dim));
		}
	}


	public Cloud[] getClouds() {
		return clouds;
	}

	private void initBag() {
		bag = new ArrayList<>();

		for(int i = 0; i < 10; i=i+5){
			bag.add(i, Student.YELLOW);
			bag.add(i+1, Student.BLUE);
			bag.add(i+2, Student.GREEN);
			bag.add(i+3, Student.PINK);
			bag.add(i+4, Student.RED);
		}
	}

	public void setMotherNature(int motherNature) {
		this.motherNature = motherNature;
	}

	public void fillBag() {
		for(int i = 0; i < 120; i = i+5){
			bag.add(i, Student.YELLOW);
			bag.add(i+1, Student.BLUE);
			bag.add(i+2, Student.GREEN);
			bag.add(i+3, Student.PINK);
			bag.add(i+4, Student.RED);
		}
	}

	public void fillIslands() {
		ArrayList<Student> students;
		int j = 0;

		students = extractStudents(10);

		for(int i = 0; i < 12; i++){
			if(i != motherNature && i != getOppositeMotherNature()){
				islands.get(i).addStudent(students.get(j));
				j++;
			}
		}
	}

	public int getOppositeMotherNature(){
		Island island = islands.get(motherNature);

		for(int i = 0; i < 6; i++){
			island = islands.getNext(island);
		}

		return islands.getPosition(island);
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

	public DoublyLinkedList getIslands() {
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
			students = clouds[numCloud].getStudentsFromCloud();
		} catch (EmptyCloudException e) {
			e.printStackTrace();
		}

		players[currentPlayer].getPlayerBoard().fillEntrance(students);
	}
}
