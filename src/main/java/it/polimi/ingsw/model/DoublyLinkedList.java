package it.polimi.ingsw.model;

/**
 * Class used for making the islands as a doubly linked list to facilitate the access
 */
public class DoublyLinkedList{
    private Node head = null;

    /**
     * Class that represents the single node of the list
     */
    class Node{
        Island island;
        Node previous;
        Node next;
        int index;
    }

    /**
     * adds an island to the list
     * @param island the island to add
     */
    public void add(Island island){

        if(head == null) {
            Node newNode = new Node();

            newNode.island = island;
            newNode.next = newNode.previous = newNode;

            head = newNode;
            newNode.index = 0;
        }

        else{
            Node last = head.previous;
            int pos = last.index;

            Node newNode = new Node();
            newNode.island = island;
            newNode.index = pos + 1;
            newNode.next = head;

            head.previous = newNode;

            newNode.previous = last;

            last.next = newNode;
        }

    }

    /**
     * removes the node from the list
     * @param index the index of the node to remove
     */
    public void remove(int index){
        Node node = head;
        Node del = new Node();


        for(int i = 0; i < this.size(); i++){
            if(node.index == index){
                del = node;
            }
            else node = node.next;
        }

        if (head == null || del == null) {
            return;
        }

        if (head == del) {
            head = del.next;
        }

        if (del.next != null) {
            del.next.previous = del.previous;
        }

        if (del.previous != null) {
            del.previous.next = del.next;
        }

        for(int i = del.index; i < this.size(); i++){
            del.next.index--;
            del = del.next;
        }
    }

    /**
     * returns the island in the index position
     * @param index the index of the asked island
     * @return the island in index position
     */
    public Island get(int index){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.index == index) return node.island;
        }

       return null;
    }

    /**
     * returns the size of the list
     * @return the size of the list
     */
    public int size(){
        if(head.previous != null) return head.previous.index+1;
        if(head != null) return 1;
        return 0;
    }

    /**
     * returns the next element of the passed island
     * @param island the island you want to know the next
     * @return the next island of the passed one
     */
    public Island getNext(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.island.equals(island)) return node.next.island;
        }

        return null;
    }

    /**
     * returns the previous island of the passed one
     * @param island the island you want to know the previous
     * @return the previous island
     */
    public Island getPrevious(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.previous;
            if(node.island.equals(island)) return node.previous.island;
        }

        return null;
    }

    /**
     * returns the index of the island
     * @param island the island you want to know the index
     * @return the index of the island
     */
    public int getPosition(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.island.equals(island)) return node.index;
        }

        return -1;
    }
}
