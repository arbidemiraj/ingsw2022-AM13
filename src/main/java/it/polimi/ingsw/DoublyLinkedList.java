package it.polimi.ingsw;

public class DoublyLinkedList{
    private Node head = null;

    class Node{
        Island island;
        Node previous;
        Node next;
        int position;
    }

    public void add(Island island){

        if(head == null) {
            Node newNode = new Node();

            newNode.island = island;
            newNode.next = newNode.previous = newNode;

            head = newNode;
            newNode.position = 0;
        }

        else{
            Node last = head.previous;
            int pos = last.position;

            Node newNode = new Node();
            newNode.island = island;
            newNode.position = pos + 1;
            newNode.next = head;

            head.previous = newNode;

            newNode.previous = last;

            last.next = newNode;
        }

    }

    public void remove(int position){
        Node node = head;
        Node del = new Node();


        for(int i = 0; i < this.size(); i++){
            if(node.position == position){
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

        for(int i = del.position; i < this.size(); i++){
            del.next.position--;
            del = del.next;
        }
    }

    public Island get(int position){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.position == position) return node.island;
        }

       return null;
    }

    public int size(){
        if(head.previous != null) return head.previous.position+1;
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

    public int getPosition(Island island){
        Node node = head;

        for(int i = 0; i < this.size(); i++){
            node = node.next;
            if(node.island.equals(island)) return node.position;
        }

        return -1;
    }
}
