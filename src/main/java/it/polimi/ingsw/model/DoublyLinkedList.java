package it.polimi.ingsw.model;

public class DoublyLinkedList{
    private Node head = null;

    class Node{
        Island island;
        Node previous;
        Node next;
        int index;
    }

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

    public Island get(int index){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.index == index) return node.island;
        }

       return null;
    }

    public int size(){
        if(head.previous != null) return head.previous.index+1;
        if(head != null) return 1;
        return 0;
    }

    public Island getNext(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.island.equals(island)) return node.next.island;
        }

        return null;
    }

    public Island getPrevious(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.previous;
            if(node.island.equals(island)) return node.previous.island;
        }

        return null;
    }

    public int getPosition(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.island.equals(island)) return node.index;
        }

        return -1;
    }
}
