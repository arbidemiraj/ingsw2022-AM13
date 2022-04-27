package it.polimi.ingsw.model;

import it.polimi.ingsw.model.DoublyLinkedList;
import it.polimi.ingsw.model.Island;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    @Test
    void add() {
        Island island = new Island();
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        doublyLinkedList.add(island);

        assertEquals(doublyLinkedList.get(0), island);
    }

    @Test
    void remove() {
        Island island = new Island();
        Island island1 = new Island();
        Island island2 = new Island();

        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        doublyLinkedList.add(island);
        doublyLinkedList.add(island1);
        doublyLinkedList.add(island2);

        doublyLinkedList.remove(1);

        assertEquals(2, doublyLinkedList.size());
        assertEquals(doublyLinkedList.get(1), island2);


    }

    @Test
    void get() {
        Island island = new Island();
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        doublyLinkedList.add(island);

        assertEquals(doublyLinkedList.get(0), island);
    }

    @Test
    void size() {
        Island island = new Island();
        Island island1 = new Island();
        Island island2 = new Island();
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        doublyLinkedList.add(island);
        doublyLinkedList.add(island1);
        doublyLinkedList.add(island2);

        assertEquals(doublyLinkedList.size(), 3);
    }

    @Test
    void getPrevious() {
        Island island = new Island();
        Island island1 = new Island();
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        doublyLinkedList.add(island);
        doublyLinkedList.add(island1);

        assertEquals(island, doublyLinkedList.getPrevious(island1));
    }

    @Test
    void getNext() {
        Island island = new Island();
        Island island1 = new Island();
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        doublyLinkedList.add(island);
        doublyLinkedList.add(island1);

        assertEquals(island1, doublyLinkedList.getNext(island));
    }

}