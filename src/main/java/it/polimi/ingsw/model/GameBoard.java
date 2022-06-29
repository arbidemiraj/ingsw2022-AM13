package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;
import it.polimi.ingsw.model.maps.IntColorMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This classes manages the game components
 */
public class GameBoard {

	private final int numPlayers;

	private DoublyLinkedList islands;

	private ArrayList<Student> bag;

	private Cloud[] clouds;

	private int motherNature;

	private final Professor[] professors;


	/**
	 * Default constructor,
	 * @param numPlayers
	 */
	public GameBoard(int numPlayers) {
		this.numPlayers = numPlayers;

		motherNature = (int) (Math.random() * 11);

		islands = new DoublyLinkedList();

		for (int i = 0; i < 12; i++) {
			Island island = new Island();
			islands.add(island);
		}


		if(numPlayers == 2) clouds = new Cloud[2];
		else clouds = new Cloud[3];

		//Create instances for clouds
		clouds[0] = new Cloud(numPlayers);
		clouds[1] = new Cloud(numPlayers);

		if(numPlayers == 3){
			clouds[2] = new Cloud(numPlayers);
		}

		//Creates 5 professors
		IntColorMap studentColorMap = new IntColorMap();
		HashMap<Integer, Student> studentColor = studentColorMap.getMap();

		professors = new Professor[5];

		for (int i = 0; i < 5; i++) {
			professors[i] = new Professor(studentColor.get(i));
		}
	}

	/**
	 * prepares the game by filling the bag, the islands and the clouds
	 */
	public void prepareGame() {
		//Initial 10 students in the bag
		initBag();

		//Create islands
		fillIslands();

		//Fill the bag with 120 students remaining
		fillBag();

		fillClouds();

		//fill entrance for each player
	}

	/**
	 * fills the clouds
	 */
	public void fillClouds() {
		int dim = numPlayers + 1;

		for (Cloud cloud : clouds) {
			try {
				cloud.addStudents(extractStudents(dim));
			} catch (EmptyBagException e) {
			}
		}
	}

	public Cloud[] getClouds() {
		return clouds;
	}

	/**
	 * initializes the bag with the first 10 students to put in the islands
	 */
	private void initBag() {
		bag = new ArrayList<>();

		for (int i = 0; i < 10; i = i + 5) {
			bag.add(i, Student.YELLOW);
			bag.add(i + 1, Student.BLUE);
			bag.add(i + 2, Student.GREEN);
			bag.add(i + 3, Student.PINK);
			bag.add(i + 4, Student.RED);
		}
	}

	public void setMotherNature(int motherNature) {
		this.motherNature = motherNature;
	}

	/**
	 * fills the bag for the start of the game
	 */
	public void fillBag() {
		for (int i = 0; i < 120; i = i + 5) {
			bag.add(i, Student.YELLOW);
			bag.add(i + 1, Student.BLUE);
			bag.add(i + 2, Student.GREEN);
			bag.add(i + 3, Student.PINK);
			bag.add(i + 4, Student.RED);
		}
	}

	/**
	 * fills the island with the students
	 */
	public void fillIslands() {
		ArrayList<Student> students = new ArrayList<>();
		int j = 0;

		try {
			students = extractStudents(10);
		} catch (EmptyBagException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 12; i++) {
			if (i != motherNature && i != getOppositeMotherNature()) {
				islands.get(i).addStudent(students.get(j));
				j++;
			}
		}
	}

	/**
	 * returns the opposite island of the one with mother nature
	 * @return
	 */
	public int getOppositeMotherNature() {
		Island island = islands.get(motherNature);

		for (int i = 0; i < 6; i++) {
			island = islands.getNext(island);
		}

		return islands.getPosition(island);
	}

	public Professor[] getProfessors() {
		return professors;
	}

	/**
	 * extracts the given number of students
	 * @param numStudents the number of students you want to extract
	 * @return the students extracted
	 * @throws EmptyBagException if the bag has been unfilled
	 */
	public ArrayList<Student> extractStudents(int numStudents) throws EmptyBagException {
		ArrayList<Student> extractedStudents = new ArrayList<>();
		
		if(bag.size() != 10 && (bag.size() - numStudents) <= 0) throw new EmptyBagException();

		int random;

		for (int i = 0; i < numStudents; i++) {
			random = (int) (Math.random() * bag.size());
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

	/**
	 * moves mother nature
	 * @param steps the number of steps you want to move mother nature
	 */
	public void moveMotherNature(int steps) {
		for(int i = 0; i < steps; i++){
			motherNature = islands.getPosition(islands.getNext(getMotherNatureIsland()));
		}
	}

	/**
	 * returns the island with mother nature on it
	 * @return the island with mother nature on it
	 */
	public Island getMotherNatureIsland() {
		return islands.get(motherNature);
	}
}
